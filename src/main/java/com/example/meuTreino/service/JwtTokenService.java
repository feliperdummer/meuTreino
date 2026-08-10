package com.example.meuTreino.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.security.userDetails.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final String ISSUER = System.getenv("JWT_ISSUER");

    public String generateToken(UserDetailsImpl user) {
        try {
            Algorithm alg = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername()) // vai ser o email do usuario, tambem unico
                    .sign(alg);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("erro ao gerar JWT", e);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(SECRET);
            return JWT.require(alg)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject(); // obtem o subject, email do usuario autenticado
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("token invalido/expirado", e);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(3).toInstant();
    }

    public String stripeToken(String jwtToken) {
        return jwtToken==null
                ? null
                : jwtToken.substring(7);
    }
}
