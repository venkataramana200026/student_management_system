package com.platformcommons.student_management.DTO;

import com.platformcommons.student_management.enums.CourseType;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class CourseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id; 
    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    private String description;

    private CourseType courseType;

    @Min(value = 1, message = "Duration should be at least 1")
    private int duration;

    private Set<String> topics;

    public CourseDTO() {}

    public CourseDTO(Long id, String courseName, String description, CourseType courseType, int duration, Set<String> topics) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.courseType = courseType;
        this.duration = duration;
        this.topics = topics;
    }
}
