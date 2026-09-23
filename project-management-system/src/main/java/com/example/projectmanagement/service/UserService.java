package com.example.projectmanagement.service;

import com.example.projectmanagement.dto.UserRequest;
import com.example.projectmanagement.dto.UserResponse;
import com.example.projectmanagement.entity.User;
import com.example.projectmanagement.entity.UserRole;
import com.example.projectmanagement.exception.ResourceNotFoundException;
import com.example.projectmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserResponse> findAll() { return repository.findAll().stream().map(this::toResponse).toList(); }
    public UserResponse findById(Long id) { return toResponse(getUser(id)); }
    public UserResponse create(UserRequest request) {
        User user = new User(null, request.name(), request.email(), request.role() == null ? UserRole.MEMBER : request.role(), null);
        return toResponse(repository.save(user));
    }
    public UserResponse update(Long id, UserRequest request) {
        User user = getUser(id); user.setName(request.name()); user.setEmail(request.email());
        if (request.role() != null) user.setRole(request.role());
        return toResponse(repository.save(user));
    }
    public void delete(Long id) { repository.delete(getUser(id)); }
    public User getUser(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found")); }
    private UserResponse toResponse(User user) { return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()); }
}
