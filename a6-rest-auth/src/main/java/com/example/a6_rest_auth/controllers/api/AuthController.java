package com.example.a6_rest_auth.controllers.api;

import com.example.a6_rest_auth.models.UserEntity;
import com.example.a6_rest_auth.models.UserPayload;
import com.example.a6_rest_auth.services.JwtService;
import com.example.a6_rest_auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String postLogin(@RequestBody UserPayload userPayload) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userPayload.email(),
                userPayload.password()
        );

        // authenticate throws a AuthenticationException when the username
        // and password are not correct
        authenticationManager.authenticate(authToken);

        return jwtService.generateToken(userPayload.email());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> postRegister(@RequestBody UserPayload userPayload) {

        UserEntity user = userService.createUser(userPayload);

        if (user == null) {
            return ResponseEntity.status(400).body(
                    Map.of("message", "email already exists")
            );
        }
        return ResponseEntity.status(200).body(user);
    }
}