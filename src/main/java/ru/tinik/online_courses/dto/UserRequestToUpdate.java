package ru.tinik.online_courses.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestToUpdate {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
}
