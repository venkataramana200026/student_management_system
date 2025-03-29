package com.platformcommons.student_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platformcommons.student_management.enums.CourseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String courseName;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Enumerated(EnumType.STRING)
	private CourseType courseType;

	@Min(1)
	private int duration;

	@ElementCollection
	@CollectionTable(name = "course_topics", joinColumns = @JoinColumn(name = "course_id"))
	@Column(name = "topic")
	private Set<String> topics = new HashSet<>();

	@Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean isActive = true;

	@ManyToMany(mappedBy = "courses")
	@JsonIgnore
	private Set<Student> students = new HashSet<>();

	public Course() {
	}

	public Course(String courseName, String description, CourseType courseType, int duration, boolean isActive) {
		this.courseName = courseName;
		this.description = description;
		this.courseType = courseType;
		this.duration = duration;
		this.isActive = isActive;
	}

	public void addStudent(Student student) {
		this.students.add(student);
		student.getCourses().add(this);
	}

	public void removeStudent(Student student) {
		this.students.remove(student);
		student.getCourses().remove(this);
	}
}
