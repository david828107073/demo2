package com.david.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String SECRET = "357238792F423F4428472B4C6250655368566Q597133743677397D2443264629";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    private JwtUtil() {
    }


    public String generateToken(Map<String, Object> payload) throws NoSuchAlgorithmException {
        return Jwts.builder().addClaims(payload)
                .setExpiration(Date.from(Instant.now().plusSeconds(60)))
                .signWith(ALGORITHM, Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }

    public Map<String, Object> parse(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }


}
