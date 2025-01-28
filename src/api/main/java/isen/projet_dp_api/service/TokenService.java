package isen.projet_dp_api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import isen.projet_dp_api.bean.SecretKeyProvider;

import java.util.Base64;
import java.util.Date;

@Log4j2
@Service
public class TokenService {

    private final SecretKeyProvider secretKeyProvider;

    public TokenService(SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider = secretKeyProvider;
    }

    public String generateToken(UserDetails userDetails) {
        log.debug(ApiStrings.GENERATING_TOKEN, userDetails.getUsername());
        // 10 hours
        var EXPIRATION_TIME_MILLISECONDS = 36000000;
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLISECONDS))
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyProvider.getSecretKey())), SignatureAlgorithm.HS512)
                .compact();
    }
}