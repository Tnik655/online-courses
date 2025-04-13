package ru.tinik.online_courses.dto;

import lombok.Getter;
import lombok.Setter;
import ru.tinik.online_courses.enums.RoleType;

@Getter
@Setter
public class UserRequestToUpdateByAdmin {
	
	private String username;
	
	private String firstName;

	private String lastName;

	private String email;
	
	private String password;
	
	private RoleType roleType;
}
