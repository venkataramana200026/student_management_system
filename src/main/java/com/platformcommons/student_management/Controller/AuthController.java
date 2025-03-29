package com.platformcommons.student_management.Controller;

import com.platformcommons.student_management.DTO.LoginRequest;
import com.platformcommons.student_management.DTO.StudentValidationRequest;
import com.platformcommons.student_management.DTO.UserDto;
import com.platformcommons.student_management.Service.AuthService;
import com.platformcommons.student_management.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
		String response = authService.authenticateAdmin(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/validate-student")
	public ResponseEntity<String> validateStudent(@Valid @RequestBody StudentValidationRequest request) {
		String response = authService.validateStudent(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userService.registerUser(userDto);
		return ResponseEntity.ok("User registered successfully.");
	}

	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome to Student Management System!");
	}
}
