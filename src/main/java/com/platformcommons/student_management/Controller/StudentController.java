package com.platformcommons.student_management.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platformcommons.student_management.DTO.StudentDTO;
import com.platformcommons.student_management.Service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Tag(name = "Student Controller", description = "Operations related to student management")
public class StudentController {

	private final StudentService studentService;

	@PreAuthorize("hasRole('STUDENT')")
	@PutMapping("/{studentId}/update-profile")
	@Operation(summary = "Update student profile", description = "Allows a student to update their profile details")
	public ResponseEntity<String> updateStudentProfile(@PathVariable("studentId") String studentId,
			@Valid @RequestBody StudentDTO updatedStudent) throws Exception {
		String response = studentService.updateStudentProfile(studentId, updatedStudent);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{studentId}/search-courses/{topic}")
	public ResponseEntity<List<String>> searchCoursesByTopic(@PathVariable("studentId") String studentId,
			@PathVariable("topic") String topic) throws Exception {
		List<String> courses = studentService.searchCoursesByTopic(studentId, topic);
		return ResponseEntity.ok(courses);
	}

	@DeleteMapping("/{studentId}/leave-course/{courseId}")
	public ResponseEntity<String> leaveCourse(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") Long courseId) throws Exception {
		String response = studentService.leaveCourse(studentId, courseId);
		return ResponseEntity.ok(response);
	}

}
