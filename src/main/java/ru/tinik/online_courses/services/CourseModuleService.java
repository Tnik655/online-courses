package ru.tinik.online_courses.services;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.CourseModuleMainInfo;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.CourseModule;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.repository.CourseModuleRepository;
import ru.tinik.online_courses.repository.CourseRepository;
import ru.tinik.online_courses.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CourseModuleService {
	
	private final CourseModuleRepository courseModuleRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public List<CourseModule> findByCourse(Long id, String username) {
		Course course = courseRepository.findById(id).orElse(null);
		if(course == null) {
			return null;
		}
		if(course.getAuthor().getUsername().equals(username)) {
			return courseModuleRepository.findByCourse(course);
		}
		return null;
	}
	
	public CourseModule findById(Long id) {
		return courseModuleRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Module not Found!"));
	}
	
	@Transactional
	public void saveModuleInfo(Long courseId, CourseModuleMainInfo updatedModule, Principal principal) {
		CourseModule module = new CourseModule();
		Course course = courseRepository.getReferenceById(courseId);
		UserPrincipal user = userRepository.findByUsername(principal.getName())
				.orElseThrow();
		
		module.setCourse(course);
		module.setTitle(updatedModule.getTitle());
		module.setDescription(updatedModule.getDescription());
		module.setCreatedBy(user);
		module.setCreatedDate(LocalDate.now());
		module.setChangedBy(user);
		module.setChangedDate(LocalDate.now());
		courseModuleRepository.save(module);
	}
	
	public void updateModuleInfo(Long moduleId, CourseModuleMainInfo updatedModule, Principal principal) {
		CourseModule module = courseModuleRepository.findById(moduleId)
				.orElseThrow();
		
		UserPrincipal user = userRepository.findByUsername(principal.getName())
				.orElseThrow();
		
		module.setTitle(updatedModule.getTitle());
		module.setDescription(updatedModule.getDescription());
		module.setChangedBy(user);
		module.setChangedDate(LocalDate.now());
		courseModuleRepository.save(module);
	}
	
}
