package com.platformcommons.student_management.Controller;

import com.platformcommons.student_management.DTO.CourseDTO;
import com.platformcommons.student_management.DTO.StudentDTO;
import com.platformcommons.student_management.Service.CourseService;
import com.platformcommons.student_management.Service.StudentService;
import com.platformcommons.student_management.model.Course;
import com.platformcommons.student_management.model.Student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/students")
@RequiredArgsConstructor
public class AdminController {

	private final StudentService studentService;
	private final CourseService courseService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<String> addStudent(@Valid @RequestBody StudentDTO student) throws Exception {
		String response = studentService.addStudent(student);
		return ResponseEntity.ok(response.equals("Student already registered") ? "Student already registered"
				: "Student has been successfully added.");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/course/add")
	public ResponseEntity<String> addCourse(@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.addCourse(courseDTO));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/course/all")
	public ResponseEntity<List<Course>> getAllCourses() {
		return ResponseEntity.ok(courseService.getAllCourses());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/course/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(courseService.getCourseById(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/course/update/{id}")
	public ResponseEntity<String> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/course/delete/{id}")
	public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
		return ResponseEntity.ok(courseService.deleteCourse(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{studentId}/assign-course/{courseId}")
	public ResponseEntity<String> assignCourseToStudent(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") Long courseId) throws Exception {
		String response = studentService.assignCourseToStudent(studentId, courseId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/by-name")
	public ResponseEntity<List<Student>> getStudentsByName(@RequestParam("name") String name) throws Exception {
		List<Student> students = studentService.findStudentsByName(name);
		return ResponseEntity.ok(students);
	}

	@GetMapping("/search/by-course/{courseId}")
	public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable("courseId") Long courseId) throws Exception {
		List<Student> students = studentService.findStudentsByCourse(courseId);
		return ResponseEntity.ok(students);
	}

}
