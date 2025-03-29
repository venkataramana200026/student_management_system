package com.platformcommons.student_management.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentValidationRequest {
	@NotBlank(message = "Student ID cannot be empty")
	private String studentId;

	@NotBlank(message = "Date of Birth cannot be empty")
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of Birth must be in format yyyy-MM-dd")
	private String dateOfBirth;
}
