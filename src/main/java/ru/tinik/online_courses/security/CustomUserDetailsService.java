package ru.tinik.online_courses.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserPrincipal> user = userRepository.findByUsername(username);
		
		return user.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found in user repository!"));
	}

}
