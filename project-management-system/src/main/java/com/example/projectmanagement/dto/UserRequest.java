package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        UserRole role
) {}
