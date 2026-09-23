package com.example.projectmanagement.service;

import com.example.projectmanagement.dto.ProjectRequest;
import com.example.projectmanagement.dto.ProjectResponse;
import com.example.projectmanagement.entity.Project;
import com.example.projectmanagement.entity.ProjectStatus;
import com.example.projectmanagement.exception.ResourceNotFoundException;
import com.example.projectmanagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final UserService userService;

    public List<ProjectResponse> findAll() { return repository.findAll().stream().map(this::toResponse).toList(); }
    public ProjectResponse findById(Long id) { return toResponse(getProject(id)); }
    public ProjectResponse create(ProjectRequest request) {
        Project project = new Project(null, request.name(), request.description(), status(request.status()), userService.getUser(request.ownerId()), null);
        return toResponse(repository.save(project));
    }
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = getProject(id); project.setName(request.name()); project.setDescription(request.description());
        if (request.status() != null) project.setStatus(request.status());
        project.setOwner(userService.getUser(request.ownerId()));
        return toResponse(repository.save(project));
    }
    public void delete(Long id) { repository.delete(getProject(id)); }
    public Project getProject(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project " + id + " not found")); }
    private ProjectStatus status(ProjectStatus value) { return value == null ? ProjectStatus.PLANNING : value; }
    private ProjectResponse toResponse(Project p) { return new ProjectResponse(p.getId(), p.getName(), p.getDescription(), p.getStatus(), p.getOwner().getId()); }
}
