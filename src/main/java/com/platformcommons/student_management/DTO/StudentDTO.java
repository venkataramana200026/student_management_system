package com.platformcommons.student_management.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class StudentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Name cannot be empty")
	private String name;

	@NotNull(message = "Date of Birth cannot be null")
	private Date dateOfBirth;

	@NotBlank(message = "Gender cannot be empty")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
	private String gender;

	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "mobile number can not be blank")
	private String mobileNumber;

	@NotEmpty(message = "Addresses cannot be empty")
	private List<AddressDTO> addresses;

	@NotEmpty(message = "Courses cannot be empty")
	private Set<CourseDTO> courses;

	@NotEmpty(message = "parents name cannot be empty")
	private String parentsName;

	public StudentDTO() {
	}

	public StudentDTO(String name, Date dateOfBirth, String gender, String email, List<AddressDTO> addresses,
			Set<CourseDTO> courses) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.email = email;
		this.addresses = addresses;
		this.courses = courses;
	}
}
