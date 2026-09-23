package com.example.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
}
