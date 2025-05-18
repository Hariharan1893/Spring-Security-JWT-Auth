package com.auth.democode.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    // Expiration time for the Jwt token - sets to 1 hour
    private static final long EXPIRATION_TIME = 3600000;

    // Secret which is like an key for encryption of our jwt token
    private static final String SECRET = "my-strong-secret-key-1893@#-you-can-change-for-your-own";

    // Key is generated using the HMAC-SHA algorithms - this key should be 32bit
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // To: Generate the JWT Token
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // To: Validate the JWT Token
    public boolean validateToken(String username, UserDetails userDetails, String token) {
        // To: Check if username is same as username in userdetails
        // To: check if token is not expired

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    // To: Extract Claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // To: Extract the JWT Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // To: Check the token is expired or not
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

}
