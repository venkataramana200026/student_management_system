package com.platformcommons.student_management.Service.impl;

import com.platformcommons.student_management.DTO.CourseDTO;
import com.platformcommons.student_management.Repository.CourseRepository;
import com.platformcommons.student_management.Service.CourseService;
import com.platformcommons.student_management.model.Course;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepo;

	@Override
	public String addCourse(CourseDTO courseDTO) {
		Optional<Course> existingCourse = courseRepo.findByCourseName(courseDTO.getCourseName());

		if (existingCourse.isPresent()) {
			return "Course already exists!";
		}

		Course course = new Course();
		course.setCourseName(courseDTO.getCourseName());
		course.setDescription(courseDTO.getDescription());
		course.setCourseType(courseDTO.getCourseType());
		course.setDuration(courseDTO.getDuration());
		course.setTopics(courseDTO.getTopics());

		courseRepo.save(course);
		return "Course added successfully!";
	}

	@Override
	public List<Course> getAllCourses() {
		return courseRepo.findByIsActiveTrue();
	}

	@Override
	public Course getCourseById(Long id) {
		return courseRepo.findByIdAndIsActiveTrue(id)
				.orElseThrow(() -> new RuntimeException("Active course not found"));
	}

	@Override
	public String updateCourse(Long id, CourseDTO courseDTO) {
		Optional<Course> courseOpt = courseRepo.findByIdAndIsActiveTrue(id);

		if (courseOpt.isEmpty()) {
			return "Active course not found!";
		}

		Course course = courseOpt.get();
		course.setCourseName(courseDTO.getCourseName());
		course.setDescription(courseDTO.getDescription());
		course.setCourseType(courseDTO.getCourseType());
		course.setDuration(courseDTO.getDuration());
		course.setTopics(courseDTO.getTopics());

		courseRepo.save(course);
		return "Course updated successfully!";
	}

	@Override
	@Transactional
	public String deleteCourse(Long id) {
		Optional<Course> courseOptional = courseRepo.findById(id);
		if (courseOptional.isEmpty()) {
			return "Course not found!";
		}

		Course course = courseOptional.get();

		course.setActive(false);
		courseRepo.save(course);

		return "Course marked as inactive.";
	}

}
