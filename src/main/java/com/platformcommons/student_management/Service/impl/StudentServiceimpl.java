package com.platformcommons.student_management.Service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.platformcommons.student_management.DTO.AddressDTO;
import com.platformcommons.student_management.DTO.CourseDTO;
import com.platformcommons.student_management.DTO.StudentDTO;
import com.platformcommons.student_management.Repository.AddressRepository;
import com.platformcommons.student_management.Repository.CourseRepository;
import com.platformcommons.student_management.Repository.StudentRepository;
import com.platformcommons.student_management.Service.StudentService;
import com.platformcommons.student_management.model.Address;
import com.platformcommons.student_management.model.Course;
import com.platformcommons.student_management.model.Student;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceimpl implements StudentService {

	private final StudentRepository studentRepo;
	private final CourseRepository courseRepo;
	private final AddressRepository addressRepo;

	@Override
	public String addStudent(StudentDTO student) throws Exception {

		Date sqlDateOfBirth = new Date(student.getDateOfBirth().getTime());

		Optional<Student> existingStudent = studentRepo.findByNameAndDateOfBirthAndGender(student.getName(),
				sqlDateOfBirth, student.getGender());
		if (existingStudent.isPresent()) {
			return "Student already registered";
		}

		Student studentObj = new Student();
		studentObj.setName(student.getName());
		studentObj.setGender(student.getGender());
		studentObj.setDateOfBirth(student.getDateOfBirth());
		studentObj.setEmail(student.getEmail());
		studentObj.setMobileNumber(student.getMobileNumber());
		studentObj.setParentsName(student.getParentsName());

		studentObj = studentRepo.save(studentObj);

		List<Address> addresses = mapAddressDTOtoEntity(student.getAddresses(), studentObj);
		studentObj.getAddresses().clear();
		studentObj.getAddresses().addAll(addresses);

		Set<Course> courses = mapCourseDTOtoEntity(student.getCourses());
		studentObj.setCourses(courses);

		studentRepo.save(studentObj);

		return "Student Registered Successfully";
	}

	private Set<Course> mapCourseDTOtoEntity(Set<CourseDTO> courseDtoList) {
		Set<Course> courseSet = new HashSet<>();

		for (CourseDTO courseDto : courseDtoList) {
			Optional<Course> existingCourse = courseRepo.findByCourseName(courseDto.getCourseName());

			if (existingCourse.isPresent()) {
				courseSet.add(existingCourse.get());
			} else {
				Course course = new Course();
				course.setCourseName(courseDto.getCourseName());
				course.setCourseType(courseDto.getCourseType());
				course.setDescription(courseDto.getDescription());
				course.setDuration(courseDto.getDuration());
				course.setTopics(courseDto.getTopics());

				course = courseRepo.save(course);
				courseSet.add(course);
			}
		}
		return courseSet;
	}

	private List<Address> mapAddressDTOtoEntity(List<AddressDTO> addressDtoList, Student student) {
		List<Address> addressList = new ArrayList<>();

		for (AddressDTO add : addressDtoList) {
			Optional<Address> existingAddress = addressRepo.findByStreetAndCityAndStateAndZipCode(add.getStreet(),
					add.getCity(), add.getState(), add.getZipCode());

			Address addObj;
			if (existingAddress.isPresent()) {
				addObj = existingAddress.get();

				if (!addObj.getStudent().equals(student)) {
					addObj = new Address();
					addObj.setStreet(add.getStreet());
					addObj.setCity(add.getCity());
					addObj.setState(add.getState());
					addObj.setZipCode(add.getZipCode());
					addObj.setAddressType(add.getAddressType());
					addObj.setStudent(student);
				}
			} else {
				addObj = new Address();
				addObj.setAddressType(add.getAddressType());
				addObj.setCity(add.getCity());
				addObj.setState(add.getState());
				addObj.setStreet(add.getStreet());
				addObj.setZipCode(add.getZipCode());
				addObj.setStudent(student);
			}

			addObj = addressRepo.save(addObj);
			addressList.add(addObj);
		}
		return addressList;
	}

	@Transactional
	public String assignCourseToStudent(String studentId, Long courseId) throws Exception {
		Optional<Student> studentOpt = studentRepo.findByStudentId(studentId);
		Optional<Course> courseOpt = courseRepo.findById(courseId);

		if (studentOpt.isPresent() && courseOpt.isPresent()) {
			Student student = studentOpt.get();
			Course course = courseOpt.get();

			student.getCourses().add(course);
			studentRepo.save(student);

			return "Course assigned successfully!";
		} else {
			return "Student or Course not found!";
		}
	}

	@Override
	public List<Student> findStudentsByName(String name) {
		return studentRepo.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<Student> findStudentsByCourse(Long courseId) throws Exception {
		Optional<Course> courseOpt = courseRepo.findById(courseId);

		if (courseOpt.isPresent()) {
			Course course = courseOpt.get();
			return new ArrayList<>(course.getStudents());
		} else {
			throw new Exception("Course not found");
		}
	}

	@Transactional
	@Override
	public String updateStudentProfile(String studentId, StudentDTO updatedStudent) throws Exception {
		Optional<Student> studentOpt = studentRepo.findByStudentId(studentId);
		if (studentOpt.isPresent()) {
			Student student = studentOpt.get();
			student.setEmail(updatedStudent.getEmail());
			student.setMobileNumber(updatedStudent.getMobileNumber());
			student.setParentsName(updatedStudent.getParentsName());

			List<Address> existingAddresses = student.getAddresses();
			List<Address> newAddresses = mapAddressDTOtoEntity(updatedStudent.getAddresses(), student);

			updateAddressList(existingAddresses, newAddresses);

			studentRepo.save(student);
			return "Profile updated successfully!";
		}
		return "Student not found!";
	}

	private void updateAddressList(List<Address> existingAddresses, List<Address> newAddresses) {
		existingAddresses.removeIf(existing -> newAddresses.stream()
				.noneMatch(newAddr -> newAddr.getId() != null && newAddr.getId().equals(existing.getId())));

		for (Address newAddr : newAddresses) {
			Optional<Address> existingAddrOpt = existingAddresses.stream()
					.filter(existing -> existing.getId() != null && existing.getId().equals(newAddr.getId()))
					.findFirst();

			if (existingAddrOpt.isPresent()) {
				Address existingAddr = existingAddrOpt.get();
				existingAddr.setCity(newAddr.getCity());
				existingAddr.setState(newAddr.getState());
				existingAddr.setStreet(newAddr.getStreet());
				existingAddr.setZipCode(newAddr.getZipCode());
			} else {
				existingAddresses.add(newAddr);
			}
		}
	}

	@Override
	public List<String> searchCoursesByTopic(String studentId, String topic) throws Exception {
		Optional<Student> studentOpt = studentRepo.findByStudentId(studentId);

		if (!studentOpt.isPresent()) {
			throw new RuntimeException("Student not found!");
		}

		Student student = studentOpt.get();
		List<String> courseNames = new ArrayList<>();

		for (Course course : student.getCourses()) {
			if (course.getTopics().contains(topic)) {
				courseNames.add(course.getCourseName());
			}
		}

		return courseNames;
	}
	
	
	@Override
    public String leaveCourse(String studentId, Long courseId) {
        Optional<Student> studentOpt = studentRepo.findByStudentId(studentId);
        Optional<Course> courseOpt = courseRepo.findById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();

            if (student.getCourses().contains(course)) {
                student.getCourses().remove(course);
                course.getStudents().remove(student);
                studentRepo.save(student);
                return "Successfully left the course!";
            } else {
                return "Student is not enrolled in this course!";
            }
        }
        return "Student or Course not found!";
    }


}
