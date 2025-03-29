package com.platformcommons.student_management.DTO;

import com.platformcommons.student_management.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String username;
    private String password;
    private Role role;
}
