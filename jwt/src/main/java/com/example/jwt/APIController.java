package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@RestController
public class APIController {

    @GetMapping("/")
    public Map<String, Object> root() {

        // Exercise
        // add an expiration date of 1 hour

        Instant inOneHour = LocalDateTime.now().plusSeconds(1).toInstant(ZoneOffset.UTC);

        String token = JWT.create()
                .withClaim("email", "joe@mail.com")
                .withClaim("userId", 123)
                .withIssuedAt(Instant.now())
                .withExpiresAt(inOneHour)
                .sign(Algorithm.HMAC256("SUPER_SECRET_KEY"));

        return Map.of(
                "status", "ok",
                "token", token
        );
    }

    @GetMapping("/verify")
    public Map<String, Object> verify(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization").split(" ")[1];
        System.out.println(token);

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("SUPER_SECRET_KEY")).build();
        try {
            DecodedJWT decoded = verifier.verify(token);
            System.out.println(decoded.getClaim("email"));
        } catch (JWTVerificationException e) {
            System.out.println(e);
            response.setStatus(401);
            return Map.of(
                    "status", "not ok"
            );
        }

        return Map.of(
                "status", "ok"
        );
    }

    // Create a SpringBoot project
    // Create a Mysql database with users table
    // create a /register Post mapping, that creates new users in the Database
    // do not forget bCrypt
    // the payload of the register with be standard for REST Apis which means JSON body
    // should have at least the email and the password
    // {
    //  "email": "someone@mail.com",
    //  "password": "12345678"
    // }
}
