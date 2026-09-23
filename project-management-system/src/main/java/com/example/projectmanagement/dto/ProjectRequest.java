package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRequest(
        @NotBlank String name,
        String description,
        ProjectStatus status,
        @NotNull Long ownerId
) {}
