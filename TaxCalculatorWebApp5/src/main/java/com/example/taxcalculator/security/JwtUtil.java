package com.example.taxcalculator.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="Y8smv2L1xiTulsrEcu6v4Ml9UNGI3ChZ"; private final long EXPIRATION=1000*60*60;
    public String generateToken(String username){ return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+EXPIRATION)).signWith(SignatureAlgorithm.HS256,SECRET).compact(); }
    public String validateToken(String token){ try{return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();}catch(Exception e){return null;} }
}
