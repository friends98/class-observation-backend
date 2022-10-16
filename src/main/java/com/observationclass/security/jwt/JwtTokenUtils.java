package com.observationclass.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.observationclass.security.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;
@Component
public class JwtTokenUtils {
    private static final Logger logger= LoggerFactory.getLogger(JwtTokenUtils.class);
    @Value("${jwtSecretKey}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs ;
    private AuthenticationManager authenticationManager;

   /* public String generateJwtToken(Authentication authentication) throws Exception{
        System.out.println("có chay vao token nhé");
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(HMAC512(jwtSecret.getBytes()));
        return token;
    }*/
   public String generateJwtToken(String userName) throws Exception{
      // System.out.println("có chay vao token nhé");
      // Claims claims = Jwts.claims().setSubject(userName);
       /*UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        JWT.create()
               .withSubject(userPrincipal.getUsername())
               .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
               .sign(HMAC512(jwtSecret.getBytes()));
       return token;*/
       String token= JWT.create()
               .withSubject(userName)
               .withExpiresAt(new Date((new Date()).getTime()+jwtExpirationMs))
               .sign(HMAC512(jwtSecret.getBytes()));
       System.out.println("day la token : "+token);
       return token;
   }
    public boolean validateJwtToke(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException ex) {
            logger.error("Invalid JWT signature");}
        catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}
