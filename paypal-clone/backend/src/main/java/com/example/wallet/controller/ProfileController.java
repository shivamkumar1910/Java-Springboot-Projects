package com.example.wallet.controller;

import com.example.wallet.dto.ChangePasswordRequest;
import com.example.wallet.dto.UpdateProfileRequest;
import com.example.wallet.dto.UserProfileDTO;
import com.example.wallet.entity.User;
import com.example.wallet.exception.ApiException;
import com.example.wallet.repository.UserRepository;
import com.example.wallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class ProfileController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new UserProfileDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        User user = userService.getCurrentUser();

        if (userRepository.existsByEmailIgnoreCase(request.getEmail()) && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            throw new ApiException("Email already exists");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User updatedUser = userRepository.save(user);

        return ResponseEntity.ok(new UserProfileDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getUsername(), updatedUser.getEmail()));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        User user = userService.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ApiException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
    }
}
