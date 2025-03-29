package com.platformcommons.student_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.platformcommons.student_management.DTO.StudentDTO;
import com.platformcommons.student_management.model.Student;

@Service
public interface StudentService {
	public String addStudent(StudentDTO student) throws Exception;

	public String assignCourseToStudent(String studentId, Long courseId) throws Exception;

	public List<Student> findStudentsByName(String name) throws Exception;

	public List<Student> findStudentsByCourse(Long courseId) throws Exception;

	public String updateStudentProfile(String studentId, StudentDTO updatedStudent) throws Exception;
	
	public List<String> searchCoursesByTopic(String studentId, String topic) throws Exception;
	
    public String leaveCourse(String studentId, Long courseId) throws Exception;

}
