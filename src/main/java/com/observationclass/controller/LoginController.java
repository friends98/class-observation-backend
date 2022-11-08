package com.observationclass.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.observationclass.common.Constants;
import com.observationclass.entity.Account;
import com.observationclass.exception.RecordNotFoundException;
import com.observationclass.model.AuthResponse;
import com.observationclass.model.TokenRequest;
import com.observationclass.security.jwt.JwtTokenUtils;
import com.observationclass.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    @Value("${clientId}")
    private String idClient;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> loginWithGoogle(@Valid @RequestBody TokenRequest token) throws Exception {
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport, factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), token.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        String email = payload.getEmail();

        if (!accountService.checkEmailExist(email)) {
            throw new RecordNotFoundException(Constants.RECORD_DOES_NOT_EXIST);
        }
        Account account = accountService.getAccountByEmail(email);
        if (account.getCampusId() != token.getCampusId()) {
            throw new RecordNotFoundException("Campus " + Constants.ERROR);
        }
        String accessToken = jwtTokenUtils.generateJwtToken(String.valueOf(account.getId()));
        return ResponseEntity.ok().body(new AuthResponse(account.getUserName(), accessToken, account.getCampusId(),account.getId(), account.getRoles()));
    }
}



