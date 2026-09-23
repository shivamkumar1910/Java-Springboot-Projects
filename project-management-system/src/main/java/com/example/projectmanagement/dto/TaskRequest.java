package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.TaskPriority;
import com.example.projectmanagement.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority,
        @NotNull Long projectId,
        Long assigneeId
) {}
