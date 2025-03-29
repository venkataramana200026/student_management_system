package com.platformcommons.student_management.Repository;

import com.platformcommons.student_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByNameContainingIgnoreCase(String name);

	@Query("SELECT s FROM Student s JOIN s.courses c WHERE c.courseName = :courseName")
	List<Student> findStudentsByCourseName(String courseName);

	Optional<Student> findByStudentId(String studentId);

	Optional<Student> findByNameAndDateOfBirthAndGender(String name, Date dateOfBirth, String gender);

}