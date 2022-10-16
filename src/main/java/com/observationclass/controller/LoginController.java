package com.observationclass.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.observationclass.entity.Account;
import com.observationclass.entity.Role;
import com.observationclass.model.AuthResponse;
import com.observationclass.model.LoginRequest;
import com.observationclass.model.TokenRequest;
import com.observationclass.security.jwt.JwtTokenUtils;
import com.observationclass.service.AccountService;
import com.observationclass.service.RoleService;
import com.observationclass.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String email;
    @Value("${clientId}")
    private String idClient;

    @Value("${clientSecret}")
    private String password;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    //http://localhost:8080/social/google
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> loginWithGoogle(@Valid @RequestBody TokenRequest token) throws Exception {
        System.out.println("day la token :" + token);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport, factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), token.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        email = payload.getEmail();
        Account account = new Account();

        if (accountService.checkEmailExist(email)) {
            System.out.println("ok nhe!!!!");
            account = accountService.getAccountByEmail(email);
            System.out.println("id cua login :" + account.getId() + "    " + account.getEmail());
            for (Role r : account.getRoles()) {
                System.out.println(r.getRoleName());
            }
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(account.getEmail());
        loginRequest.setPassword(password);
        System.out.println("day la pass :" + password);
        System.out.println("day la respone: ");
        String userName=account.getUserName();
        System.out.println("sao lai ko chay den day");
       //Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        String accessToken=jwtTokenUtils.generateJwtToken(userName);
        System.out.println("access token : "+accessToken);

        return ResponseEntity.ok().body(new AuthResponse(account.getUserName(),accessToken,account.getRoles()));
    }
}



