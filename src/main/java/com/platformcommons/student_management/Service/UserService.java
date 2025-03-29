package com.platformcommons.student_management.Service;

import com.platformcommons.student_management.DTO.UserDto;
import com.platformcommons.student_management.Exception.UserAlreadyExistsException;
import com.platformcommons.student_management.enums.Role;
import com.platformcommons.student_management.model.User;
import com.platformcommons.student_management.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

}
