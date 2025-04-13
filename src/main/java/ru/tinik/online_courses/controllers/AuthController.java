package ru.tinik.online_courses.controllers;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/reg")
	public String regPage() {
		return "reg";
	}
	
	@PostMapping("/reg")
	public String regUser(UserPrincipal newUser, HttpServletRequest request, HttpServletResponse response) {
		userService.save(newUser, request, response);
		return "redirect:/profile";
	}
}
