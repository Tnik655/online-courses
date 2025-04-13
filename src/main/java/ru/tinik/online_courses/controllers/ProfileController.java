package ru.tinik.online_courses.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.UserRequestToUpdate;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.UserService;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
	
	private final UserService userService;
	
	@GetMapping
	public String showProfile(Principal principal, Model model) {
		UserPrincipal user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		return "profile";
	}
	
	@PostMapping
	public String updateProfile(Principal principal, UserRequestToUpdate updatedUser) {
		userService.update(principal.getName(), updatedUser);
		return "redirect:/courses";
	}
}
