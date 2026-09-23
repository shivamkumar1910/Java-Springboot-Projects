package com.example.wallet.service;

import com.example.wallet.dto.UserSummaryDTO;
import com.example.wallet.entity.User;
import com.example.wallet.exception.ApiException;
import com.example.wallet.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ApiException("Unauthorized request");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) {
            return userRepository.findById(user.getId())
                    .orElseThrow(() -> new ApiException("User not found"));
        }

        String username = authentication.getName();
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ApiException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ApiException("User not found"));
    }

    public List<UserSummaryDTO> searchUsers(String username) {
        return userRepository.findAll().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                .map(user -> new UserSummaryDTO(user.getUsername(), user.getName()))
                .collect(Collectors.toList());
    }
}
