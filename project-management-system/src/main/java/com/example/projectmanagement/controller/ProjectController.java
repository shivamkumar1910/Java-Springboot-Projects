package com.example.projectmanagement.controller;

import com.example.projectmanagement.dto.ProjectRequest;
import com.example.projectmanagement.dto.ProjectResponse;
import com.example.projectmanagement.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    @GetMapping public List<ProjectResponse> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public ProjectResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProjectResponse create(@Valid @RequestBody ProjectRequest request) { return service.create(request); }
    @PutMapping("/{id}") public ProjectResponse update(@PathVariable Long id, @Valid @RequestBody ProjectRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
