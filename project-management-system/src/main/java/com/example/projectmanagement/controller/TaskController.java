package com.example.projectmanagement.controller;

import com.example.projectmanagement.dto.TaskRequest;
import com.example.projectmanagement.dto.TaskResponse;
import com.example.projectmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService service;
    @GetMapping public List<TaskResponse> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public TaskResponse findById(@PathVariable Long id) { return service.findById(id); }
    @GetMapping("/project/{projectId}") public List<TaskResponse> findByProject(@PathVariable Long projectId) { return service.findByProject(projectId); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public TaskResponse create(@Valid @RequestBody TaskRequest request) { return service.create(request); }
    @PutMapping("/{id}") public TaskResponse update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
