package com.observationclass.security.jwt;

import com.auth0.jwt.JWT;
import com.observationclass.entity.Account;
import com.observationclass.repository.AccountRepository;
import com.observationclass.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
@Service

public class AuthTokenFilter extends BasicAuthenticationFilter {


    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AccountRepository accountRepository;
    public AuthTokenFilter(AuthenticationManager authenticationManager, AccountRepository accountReqository) {
        super(authenticationManager);
        this.accountRepository=accountReqository;

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization")
                    .replace("Bearer ","");
            if(token!=null){
               // jwtTokenUtils.getEmailFromToken();
                String email = JWT.require(HMAC512("classobservation"))
                        .build()
                        .verify(token)
                        .getSubject();

                if (email != null) {
                    Account account = accountRepository.findByEmail(email).get();
                    UserPrincipal principal = new UserPrincipal(account);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, principal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage()+"hhhhhhhhhhhhhhhhhh");
            logger.error("Cannot set user authentication: " + e);
        }
        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

}
