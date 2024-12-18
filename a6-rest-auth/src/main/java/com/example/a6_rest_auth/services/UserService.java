package com.example.a6_rest_auth.services;

import com.example.a6_rest_auth.models.UserEntity;
import com.example.a6_rest_auth.models.UserPayload;
import com.example.a6_rest_auth.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService  {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
//                .roles("ADMIN")
                .build();
    }

    public UserEntity createUser(UserPayload userPayload) {
        if (userRepository.findByEmail(userPayload.email()).isPresent()) {
            return null;
        }

        UserEntity user = new UserEntity();

        user.setEmail(userPayload.email());
        user.setPassword(new BCryptPasswordEncoder().encode(userPayload.password()));

        return userRepository.save(user);
    }
}