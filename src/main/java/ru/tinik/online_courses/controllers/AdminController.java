package ru.tinik.online_courses.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.UserRequestToUpdateByAdmin;
import ru.tinik.online_courses.enums.RoleType;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.UserService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final UserService userService;
	
	@GetMapping("/users")
	public String showAdminPanael(Model model) {
		List<UserPrincipal> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "admin/users";
	}
	
	@GetMapping("/users/{id}")
	public String showUserDetails(@PathVariable Long id, Model model) {
		UserPrincipal user = userService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("roles", RoleType.values());
		return "admin/user-details";
	}
	
	@PostMapping("/users/{id}")
	public String changeUserDetails(@PathVariable Long id, UserRequestToUpdateByAdmin user) {
		userService.update(id, user);
		return "redirect:/admin/users/{id}";
	}
}
