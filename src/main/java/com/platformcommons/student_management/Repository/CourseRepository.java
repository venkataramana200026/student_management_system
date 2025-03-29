package com.platformcommons.student_management.Repository;

import com.platformcommons.student_management.model.Course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findByCourseName(String courseName);

	List<Course> findByIsActiveTrue();

	Optional<Course> findByIdAndIsActiveTrue(Long id);

}