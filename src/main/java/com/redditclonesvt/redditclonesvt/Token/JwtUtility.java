package com.redditclonesvt.redditclonesvt.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtUtility {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpireTime;

    public String generateToken(Authentication authentication) throws Exception {
        User p = (User) authentication.getPrincipal();
       return Jwts.builder().setSubject(p.getUsername())
               .setIssuedAt(Date.from(Instant.now()))
               .signWith(getPrivateKey())
               .setExpiration(Date.from(Instant.now().plusMillis(jwtExpireTime)))
               .compact();
    }

    public long getTokenExpirationTime(){
        return jwtExpireTime;
    }

    @PostConstruct
    public void init() throws Exception {
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream is = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(is, "secret".toCharArray());
        }catch(KeyStoreException | IOException | NoSuchAlgorithmException exception) {
            throw new Exception("Problem u ucitavanju keystore!");
        }
    }

    private PrivateKey getPrivateKey() throws Exception {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException exception){
            throw new Exception("Problem u fetchovanju privatnog kljuca!");
        }
    }

    public boolean validate(String token) throws Exception {
        Jwts.parser().setSigningKey(getPublicKey());
        return true;
    }

    private PublicKey getPublicKey() throws Exception {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException exception){
            throw new Exception("Problem u fetchovanju privatnog kljuca!");
        }
    }

    public String getUsernameFromToken(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(getPublicKey())
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String generateTokenWithUsername(String username) throws Exception {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpireTime)))
                .compact();
    }
}
