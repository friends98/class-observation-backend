package com.observationclass.security.jwt;

import com.auth0.jwt.JWT;
import com.observationclass.entity.Account;
import com.observationclass.repository.AccountReqository;
import com.observationclass.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthTokenFilter extends BasicAuthenticationFilter {


    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwtSecretKey}")
    private String jwtSecret;
    @Autowired
    private AccountReqository accountRepository;
    public AuthTokenFilter(AuthenticationManager authenticationManager,AccountReqository accountReqository) {
        super(authenticationManager);
        this.accountRepository=accountReqository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("abc"+header);
        if (header == null || !header.startsWith("Bearer")) {
            System.out.println("chay vo doan nay");
            filterChain.doFilter(request, response);

            return;
        }
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization")
                .replace("Bearer", "");
        if (token != null) {
            String email = JWT.require(HMAC512(jwtSecret))
                    .build()
                    .verify(token)
                    .getSubject();
            if (email != null) {
                Account account = accountRepository.findByEmail(email).get();
                UserPrincipal principal = new UserPrincipal(account);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
