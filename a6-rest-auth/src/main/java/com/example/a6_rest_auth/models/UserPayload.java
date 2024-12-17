package com.example.a6_rest_auth.models;

public record UserPayload(
        String email,
        String password
) {
}