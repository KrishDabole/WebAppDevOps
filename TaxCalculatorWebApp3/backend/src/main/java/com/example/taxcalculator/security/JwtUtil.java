package com.example.taxcalculator.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private final Key key;
    private final long expirationMs;
    public JwtUtil(@Value("${security.jwt.secret}") String secret, @Value("${security.jwt.expiration-ms}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }
    public String generateToken(String userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder().setSubject(userId).setIssuedAt(now).setExpiration(exp).signWith(key, SignatureAlgorithm.HS256).compact();
    }
    public String validateAndGetUserId(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
