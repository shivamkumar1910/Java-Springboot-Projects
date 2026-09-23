package com.example.projectmanagement.repository;

import com.example.projectmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
