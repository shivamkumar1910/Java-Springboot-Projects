package com.example.projectmanagement.controller;

import com.example.projectmanagement.dto.UserRequest;
import com.example.projectmanagement.dto.UserResponse;
import com.example.projectmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @GetMapping public List<UserResponse> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public UserResponse findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public UserResponse create(@Valid @RequestBody UserRequest request) { return service.create(request); }
    @PutMapping("/{id}") public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
