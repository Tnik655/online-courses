package ru.tinik.online_courses.controllers;



import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.CourseModuleMainInfo;
import ru.tinik.online_courses.models.CourseModule;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.CourseModuleService;
import ru.tinik.online_courses.services.CourseService;
import ru.tinik.online_courses.services.UserService;

@Controller
@RequestMapping(value = "/edit-module/{moduleId}")
@RequiredArgsConstructor
public class ModuleController {
	
	private final CourseService courseService;
	private final CourseModuleService courseModuleService;
	private final UserService userService;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	@ModelAttribute("module")
	public CourseModule module(@PathVariable Long moduleId) {
		return courseModuleService.findById(moduleId);
	}
	
	@GetMapping
	@PreAuthorize("@accessControlService.isModuleOwner(#moduleId, principal.username)")
	public String showEditModulePage(@PathVariable Long moduleId) {
		return "teacher/module";
	}
	
	@PostMapping
	@PreAuthorize("@accessControlService.isModuleOwner(#moduleId, principal.username)")
	public String saveModuleMainInfo(
			@PathVariable Long moduleId, 
			Principal principal,
			CourseModuleMainInfo module) {
		
		courseModuleService.updateModuleInfo(moduleId, module, principal);
		return "redirect:/edit-module/" + moduleId;
	}
}
