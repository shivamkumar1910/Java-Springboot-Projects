package com.example.projectmanagement.dto;

import com.example.projectmanagement.entity.ProjectStatus;

public record ProjectResponse(Long id, String name, String description, ProjectStatus status, Long ownerId) {}
