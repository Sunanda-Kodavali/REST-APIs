package com.example.a6_rest_auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    private final int expiresInMinutes = 10 * 60;
    private final String superSecretKey = "SUPER_SECRET_KEY";

    public String generateToken(String email) {
        Instant issuedAtInstant = LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC);
        Instant expiresAtInstant = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(expiresInMinutes).toInstant(ZoneOffset.UTC);

        return JWT.create()
                .withClaim("email", email)
                .withIssuedAt(issuedAtInstant)
                .withExpiresAt(expiresAtInstant)
                .sign(Algorithm.HMAC256(superSecretKey));
    }

    public String extractEmail(String token) {
        JWTVerifier verifier = JWT.require(
                Algorithm.HMAC256(superSecretKey)
        ).build();

        DecodedJWT decoded = verifier.verify(token);

        return decoded.getClaim("email").asString();
    }
}