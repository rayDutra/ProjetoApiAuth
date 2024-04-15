package com.nttdata.authentication.api.service;


import com.nttdata.authentication.api.client.User;
import com.nttdata.authentication.api.client.ValidationsClient;
import com.nttdata.authentication.api.exception.AuthException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ValidationsClient validationsClient;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;

    @Override
    public String authenticate(String email, String password) {
        User user = validationsClient.checkEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getSenha())) {
            return generateJwtToken(email);
        } else {
            throw new AuthException("Credenciais inválidas");
        }
    }

    @Override
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateJwtToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
