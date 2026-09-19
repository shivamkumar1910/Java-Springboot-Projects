package com.example.wallet.controller;

import com.example.wallet.dto.UserSummaryDTO;
import com.example.wallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSummaryDTO>> searchUsers(@RequestParam String username) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }
}
