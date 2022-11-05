package com.observationclass.security.jwt;

import com.auth0.jwt.JWT;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
@Component
public class JwtTokenUtils {
    private static final Logger logger= LoggerFactory.getLogger(JwtTokenUtils.class);

    @Value("${jwt.SecretKey}")
    private String jwtSecret;

    @Value("${jwt.ExpirationMs}")
    private int jwtExpirationMs ;
    //private AuthenticationManager authenticationManager;

   public String generateJwtToken(String userName) throws Exception{
       String token= JWT.create()
               .withSubject(userName)
               .withExpiresAt(new Date((new Date()).getTime()+jwtExpirationMs))
               .sign(HMAC512(jwtSecret.getBytes()));
       return token;
   }
    public boolean validateJwtToken(String authToken){
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
    //public void getEmailFromToken(){
      //  System.out.println("");
    //}

}
