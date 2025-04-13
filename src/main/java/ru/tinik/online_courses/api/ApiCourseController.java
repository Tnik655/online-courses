package ru.tinik.online_courses.api;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.CourseRequestToUpdate;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.services.CourseService;
import ru.tinik.online_courses.services.UserService;

@RestController
@RequestMapping("api/course")
@RequiredArgsConstructor
public class ApiCourseController {

	private final CourseService courseService;
	private final UserService userService;

//	@GetMapping
//	public List<Course> courseTable() {
//		return courseService.findAll();
//	}
//
//	@GetMapping("/{id}")
//	public Course showCourse(@PathVariable Long id) {
//		return courseService.findById(id).orElseThrow();
//	}

//	
//	@GetMapping("/filter")
//	public List<Course> filteredCources(@RequestParam(required = false) String prefix){
//		return courseService.findByTitleWithPrefix(Objects.requireNonNullElse(prefix, ""));
//	}
//	
//	@PutMapping("/{id}")
//	public void updateCource(@PathVariable Long id,
//								@Valid @RequestBody CourseRequestToUpdate courseRequest) {
//		courseService.update(id, courseRequest);
//	}
//	
	@PostMapping
	public void addCourse(@Valid @RequestBody Course course) {
		courseService.save(course);
	}
	// TODO доделать в сервисе
//	@DeleteMapping("/{id}")
//	public void deleteCourse(@PathVariable Long id) {
//		courseService.delete(id);
//	}

//	@PostMapping("/{courseId}/assign")
//	public void assignCourseWithUser(@PathVariable("courseId") Long courseId,
//								@RequestParam("userId") Long id) {
//		userService.assignCourse(courseId, id);
//	}
//	
//	@PutMapping("/{courseId}/dismiss")
//	public void dismissCourseWithUser(@PathVariable("courseId") Long courseId,
//								@RequestParam("userId") Long id) {
//		userService.dismissCourse(courseId, id);
//	}

}
