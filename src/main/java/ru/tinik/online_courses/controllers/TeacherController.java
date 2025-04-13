package ru.tinik.online_courses.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.CourseModuleMainInfo;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.CourseModule;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.CourseModuleService;
import ru.tinik.online_courses.services.CourseService;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

	private final CourseService courseService;
	private final CourseModuleService courseModuleService;
	
	@GetMapping
	public String showTeacherPage(Model model, Principal principal) {
		List<Course> courses = courseService.findByAuthor(principal);
		
		model.addAttribute("courses", courses);
		return "teacher/teacher";
	}
	
	@GetMapping("/create")
	public String showCreateCoursePage() {
		return "teacher/create-course";
	}

	@GetMapping("/{courseId}")
	public String showEditCoursePage(@PathVariable Long courseId, Model model, Principal principal) {
		
		List<CourseModule> modules = courseModuleService.findByCourse(courseId, principal.getName());
		if(modules == null) {
			return "redirect:/teacher";
		}
		model.addAttribute("modules", modules);
		model.addAttribute("course", courseService.findById(courseId).orElseThrow());
		return "teacher/edit-course";
	}
	
	@PostMapping("/create")
	public String createNewCourse(String title, Principal principal) {
		Long id = courseService.createByTitleAndAuthorAndGetId(title, principal);
		if(id.equals(null)) return "redirect:/teacher";
		return "redirect:/teacher/" + id;
	}
	
	@PostMapping("/{courseId}/delete")
	public String deleteCourse(@PathVariable Long courseId, Principal principal) {
		courseService.setDeletedCourse(courseId, principal);
		return "redirect:/teacher";
	}
	
	@PostMapping("/{courseId}")
	public String saveNewModule(
			@PathVariable Long courseId, 
			Principal principal,
			CourseModuleMainInfo newModule) {
		courseModuleService.saveModuleInfo(courseId, newModule, principal);
		return "redirect:/teacher";
	}
	
	@PostMapping("/{courseId}/save-passing-time")
	public String savePassingTime(
			@PathVariable Long courseId, 
			Principal principal,
			Short passingTime) {
		courseService.savePassingTime(courseId, principal, passingTime);
		return "redirect:/teacher";
	}
 	
}
