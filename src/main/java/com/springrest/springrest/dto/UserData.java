package com.springrest.springrest.dto;

import com.springrest.springrest.entity.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserData {

    Long userId;
    String email;
    String userName;
    @Enumerated(EnumType.STRING)
    private Role role;

}
