package com.platformcommons.student_management.Service;

import org.springframework.stereotype.Service;

import com.platformcommons.student_management.DTO.LoginRequest;
import com.platformcommons.student_management.DTO.StudentValidationRequest;

@Service
public interface AuthService {

	String authenticateAdmin(LoginRequest request);

	String validateStudent(StudentValidationRequest request);

}
