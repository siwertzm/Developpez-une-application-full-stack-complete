package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Nom d'utilisateur déjà utilisé");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getId(), savedUser.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getLogin())
            .or(() -> userRepository.findByUsername(request.getLogin()))
            .orElseThrow(() -> new BadCredentialsException("Identifiants invalides"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Identifiants invalides");
        }

        String token = jwtService.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token);
    }
}