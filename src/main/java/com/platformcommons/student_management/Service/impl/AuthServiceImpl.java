package com.platformcommons.student_management.Service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.platformcommons.student_management.DTO.LoginRequest;
import com.platformcommons.student_management.DTO.StudentValidationRequest;
import com.platformcommons.student_management.Repository.StudentRepository;
import com.platformcommons.student_management.Repository.UserRepository;
import com.platformcommons.student_management.Service.AuthService;
import com.platformcommons.student_management.model.Student;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;

	public AuthServiceImpl(StudentRepository studentRepository, UserRepository userRepository,
			AuthenticationManager authenticationManager) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String authenticateAdmin(LoginRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		if (authentication.isAuthenticated()) {
			return "Login Successful!";
		} else {
			throw new RuntimeException("Invalid Admin Credentials");
		}
	}

	@Override
	public String validateStudent(StudentValidationRequest request) {
		Optional<Student> studentOpt = studentRepository.findByStudentId(request.getStudentId());

		if (studentOpt.isPresent()) {
			Student student = studentOpt.get();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			if (dateFormat.format(student.getDateOfBirth()).equals(request.getDateOfBirth())) {
				return "Student validation successful!";
			}
			return "Invalid date of birth!";
		}
		return "Student not found!";
	}
}