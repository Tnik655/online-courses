package ru.tinik.online_courses.services;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.UserRequestToUpdate;
import ru.tinik.online_courses.dto.UserRequestToUpdateByAdmin;
import ru.tinik.online_courses.enums.RoleType;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.Role;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.repository.CourseRepository;
import ru.tinik.online_courses.repository.RoleRepository;
import ru.tinik.online_courses.repository.UserRepository;
import ru.tinik.online_courses.security.CustomUserDetailsService;

@Service
@RequiredArgsConstructor
public class UserService {

	private final SecurityContextRepository contextRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final CourseRepository courseRepository;
	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsService userDetailsService;
	
	@Transactional
	public boolean save(UserPrincipal user, HttpServletRequest request, HttpServletResponse response) {
		if(userRepository.findByUsername(user.getUsername()).orElse(null) != null) return false;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRegistrationDate(LocalDate.now());
		user.setRole(roleRepository.findByType(RoleType.ROLE_USER).orElseThrow());
		userRepository.save(user);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		
		SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
		SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
		securityContext.setAuthentication(authentication);
		
		securityContextHolderStrategy.setContext(securityContext);
		contextRepository.saveContext(securityContext, request, response);
		return true;
	}
	
	@Transactional
	public void update(String username, UserRequestToUpdate updatedUser) {
		UserPrincipal user = userRepository.findByUsername(username).orElseThrow();
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setEmail(updatedUser.getEmail());
		userRepository.save(user);
	}
	
	@Transactional
	public void update(Long id, UserRequestToUpdateByAdmin updatedUser) {
		UserPrincipal user = findById(id);
		user.setUsername(updatedUser.getUsername());
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setEmail(updatedUser.getEmail());
		user.setPassword(updatedUser.getPassword());
		
		Role role = roleRepository.findByType(updatedUser.getRoleType()).orElseThrow();
		user.setRole(role);
		userRepository.save(user);
	}

	public void deleteById(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("User not found!");
		}
	}
	
	public UserPrincipal findByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Not user with username: " + username));
	}
	
	public List<UserPrincipal> findAllUsers(){
		return userRepository.findAll();
	}
	
	public UserPrincipal findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found!"));
	}

	@Transactional
	public void assignCourse(Long courseId, Long userId) {
		UserPrincipal user = userRepository.findById(userId).orElseThrow();
		Course course = courseRepository.findById(courseId).orElseThrow();

		user.addCourse(course);
	}

	@Transactional
	public void dismissCourse(Long courseId, Long userId) {
		UserPrincipal user = userRepository.findById(userId).orElseThrow();
		Course course = courseRepository.findById(courseId).orElseThrow();

		user.removeCourse(course);
	}
}
