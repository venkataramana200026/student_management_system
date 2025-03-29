package com.platformcommons.student_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.platformcommons.student_management.DTO.CourseDTO;
import com.platformcommons.student_management.model.Course;

@Service
public interface CourseService {
	String addCourse(CourseDTO courseDTO);

	List<Course> getAllCourses();

	Course getCourseById(Long id);

	String updateCourse(Long id, CourseDTO courseDTO);

	String deleteCourse(Long id);

}
