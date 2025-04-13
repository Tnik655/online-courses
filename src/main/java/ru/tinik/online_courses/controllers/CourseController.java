package ru.tinik.online_courses.controllers;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.services.CourseService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
	
	private final CourseService courseService;
	
	@GetMapping
	public String showCoursePage(Model model) {
		List<Course> courseTable = courseService.findWithAuthor();
		model.addAttribute("courses", courseTable);
		return "courses";
	}
}
