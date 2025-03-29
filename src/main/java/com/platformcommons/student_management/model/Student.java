package com.platformcommons.student_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String studentId;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	private String gender;

	private String email;

	private String mobileNumber;

	private String parentsName;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses = new HashSet<>();

	public Student(String name, Date dateOfBirth, String gender) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}

	public Student() {
	}

	public void enrollInCourse(Course course) {
		this.courses.add(course);
		course.getStudents().add(this);
	}
}
