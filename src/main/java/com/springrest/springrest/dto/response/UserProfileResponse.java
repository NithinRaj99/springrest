package com.springrest.springrest.dto.response;

import lombok.Data;

@Data
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
}
