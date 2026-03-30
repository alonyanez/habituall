package com.javier.habituall.security;

import java.security.Key;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtUtil{

     private final String secret;
     private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret, 
                    @Value("${jwt.expiration}") Long expiration) {
        
        this.secret = secret;
        this.expiration = expiration;
    }

    private Key getSigningKey() {
    // Aquí deberías convertir tu secret a una clave de firma adecuada
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .signWith(getSigningKey())
            .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
            .compact();
    }

    public String extractEmail(String token) {
        // Implementa la lógica para extraer el email del token
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
