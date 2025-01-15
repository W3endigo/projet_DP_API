package isen.projet_dp_api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetails userDetails) {
        // 10 hours
        var EXPIRATION_TIME_MILLISECONDS = 36000000;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLISECONDS))
                .signWith(Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes())), SignatureAlgorithm.HS512)
                .compact();
    }
}
