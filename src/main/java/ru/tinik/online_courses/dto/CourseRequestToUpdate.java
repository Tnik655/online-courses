package ru.tinik.online_courses.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestToUpdate {

	@NotBlank(message = "Course authore has to be filled")
	private String author;

	@NotBlank(message = "Course title has to be filled")
	private String title;
}
