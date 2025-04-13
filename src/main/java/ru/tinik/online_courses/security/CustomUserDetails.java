package ru.tinik.online_courses.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import ru.tinik.online_courses.models.UserPrincipal;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails{

	private UserPrincipal user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(user.getRole().getType());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
}
