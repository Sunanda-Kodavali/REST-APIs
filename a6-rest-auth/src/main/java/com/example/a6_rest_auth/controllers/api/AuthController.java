package com.example.a6_rest_auth.controllers.api;

import com.example.a6_rest_auth.models.UserEntity;
import com.example.a6_rest_auth.models.UserPayload;
import com.example.a6_rest_auth.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String postLogin() {
        return "";
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