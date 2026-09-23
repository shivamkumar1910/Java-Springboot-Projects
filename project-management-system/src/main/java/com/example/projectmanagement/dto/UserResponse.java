package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.UserRole;

public record UserResponse(Long id, String name, String email, UserRole role) {}
