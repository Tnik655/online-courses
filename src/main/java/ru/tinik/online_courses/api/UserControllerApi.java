package ru.tinik.online_courses.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.services.UserService;

@RestController

@RequiredArgsConstructor
public class UserControllerApi {

	private final UserService userService;

//	@PostMapping("/reg")
//	public void addUserApi(@RequestBody UserPrincipal newUser) {
//		userService.save(newUser);
//	}

	@DeleteMapping("/remove/{id}")
	public void removeUser(@PathVariable Long id) {
		userService.deleteById(id);
	}
}
