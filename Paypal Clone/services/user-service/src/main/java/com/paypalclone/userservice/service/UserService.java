package com.paypalclone.userservice.service;

import com.paypalclone.userservice.dto.AuthResponse;
import com.paypalclone.userservice.dto.LoginRequest;
import com.paypalclone.userservice.dto.RegisterRequest;
import com.paypalclone.userservice.entity.User;
import com.paypalclone.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepository.save(user);
        return new AuthResponse("demo-token", saved.getEmail(), saved.getName());
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail().trim());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new AuthResponse("demo-token", user.getEmail(), user.getName());
    }
}
