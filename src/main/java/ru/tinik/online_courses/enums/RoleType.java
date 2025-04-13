package ru.tinik.online_courses.enums;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public enum RoleType implements GrantedAuthority {
	
	ROLE_ADMIN, ROLE_USER, ROLE_TEACHER;

	@Override
	public String getAuthority() {
		return name();
	}
}
