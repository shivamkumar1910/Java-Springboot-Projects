package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.TaskPriority;
import com.example.projectmanagement.entity.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(Long id, String title, String description, LocalDate dueDate,
                           TaskStatus status, TaskPriority priority, Long projectId, Long assigneeId) {}
