package com.example.springbootpracticemall.security;

import com.example.springbootpracticemall.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

public class JwtService {

    private final SecretKey secretKey;
    private final int validSeconds;
    private final JwtParser jwtParser;

    public JwtService(String secretKeyStr, int validSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.validSeconds = validSeconds;
    }

    public String createLoginAccessToken(CustomUserDetails user) {
        long expirationMillis = Instant.now()
                .plusSeconds(validSeconds)
                .getEpochSecond()
                * 1000;
        Claims claims = Jwts.claims()
                .issuedAt(new Date())
                .expiration(new Date(expirationMillis))
                .add("email", user.getEmail())
                .add("username", user.getUsername())
                .add("authorities", user.getAuthorities())
                .build();

        // 簽名後產生 JWT
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();

    }

    public Claims parseToken(String jwt) throws JwtException {
        return jwtParser.parseSignedClaims(jwt).getPayload();
    }
}
