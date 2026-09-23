package com.example.projectmanagement.service;

import com.example.projectmanagement.dto.TaskRequest;
import com.example.projectmanagement.dto.TaskResponse;
import com.example.projectmanagement.entity.*;
import com.example.projectmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final ProjectService projectService;
    private final UserService userService;

    public List<TaskResponse> findAll() { return repository.findAll().stream().map(this::toResponse).toList(); }
    public List<TaskResponse> findByProject(Long projectId) { projectService.getProject(projectId); return repository.findByProjectId(projectId).stream().map(this::toResponse).toList(); }
    public TaskResponse findById(Long id) { return toResponse(repository.findById(id).orElseThrow(() -> new com.example.projectmanagement.exception.ResourceNotFoundException("Task " + id + " not found"))); }
    public TaskResponse create(TaskRequest r) { return toResponse(repository.save(new Task(null, r.title(), r.description(), r.dueDate(), value(r.status(), TaskStatus.TODO), value(r.priority(), TaskPriority.MEDIUM), projectService.getProject(r.projectId()), r.assigneeId() == null ? null : userService.getUser(r.assigneeId())))); }
    public TaskResponse update(Long id, TaskRequest r) { Task t = repository.findById(id).orElseThrow(() -> new com.example.projectmanagement.exception.ResourceNotFoundException("Task " + id + " not found")); t.setTitle(r.title()); t.setDescription(r.description()); t.setDueDate(r.dueDate()); if (r.status() != null) t.setStatus(r.status()); if (r.priority() != null) t.setPriority(r.priority()); t.setProject(projectService.getProject(r.projectId())); t.setAssignee(r.assigneeId() == null ? null : userService.getUser(r.assigneeId())); return toResponse(repository.save(t)); }
    public void delete(Long id) { repository.delete(repository.findById(id).orElseThrow(() -> new com.example.projectmanagement.exception.ResourceNotFoundException("Task " + id + " not found"))); }
    private <T> T value(T value, T fallback) { return value == null ? fallback : value; }
    private TaskResponse toResponse(Task t) { return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getDueDate(), t.getStatus(), t.getPriority(), t.getProject().getId(), t.getAssignee() == null ? null : t.getAssignee().getId()); }
}
