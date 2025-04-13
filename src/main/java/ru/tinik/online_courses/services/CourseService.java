package ru.tinik.online_courses.services;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.tinik.online_courses.controllers.CourseController;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.tinik.online_courses.dto.CourseRequestToUpdate;
import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.CourseModule;
import ru.tinik.online_courses.models.UserPrincipal;
import ru.tinik.online_courses.repository.CourseModuleRepository;
import ru.tinik.online_courses.repository.CourseRepository;
import ru.tinik.online_courses.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final UserRepository userRepository;



	public List<Course> findByTitleWithPrefix(String prefix) {
		return courseRepository.findByTitleLike(prefix + "%");
	}

	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public List<Course> findWithAuthor() {
		return courseRepository.findWithAuthor();
	}

	public Optional<Course> findById(Long id) {
		return courseRepository.findById(id);
	}

	public void update(Long id, CourseRequestToUpdate courseRequest) {
		Course course = courseRepository.findById(id).orElseThrow();
		// course.setAuthor(courseRequest.getAuthor());
		course.setTitle(courseRequest.getTitle());
		courseRepository.save(course);
	}
	
	public List<Course> findByAuthor(Principal principal){
		UserPrincipal author = userRepository.findByUsername(principal.getName()).orElseThrow();
		return courseRepository.findByAuthor(author);
	}
	
	@Transactional
	public Long createByTitleAndAuthorAndGetId(String title, Principal principal) {
		UserPrincipal author = userRepository.findByUsername(principal.getName()).orElseThrow();
		Course course = new Course();
		course.setTitle(title);
		course.setAuthor(author);
		course.setCreatedBy(author);
		courseRepository.save(course);
		return courseRepository.lastInsertId();
	}

	public void save(Course course) {
		courseRepository.save(course);
	}
	
	@Transactional
	public void savePassingTime(
			Long courseId, 
			Principal principal,
			Short passingTime) {
		Course course = courseRepository.findById(courseId).orElseThrow();
		UserPrincipal user = userRepository.findByUsername(principal.getName()).orElseThrow();
		if(course.getCreatedBy() == user) {
			course.setPassingTime(passingTime);
			courseRepository.save(course);
		}
	}

	@Transactional
	public void setDeletedCourse(Long id, Principal principal) {
		UserPrincipal deletedBy = userRepository.findByUsername(principal.getName()).orElseThrow();
		Course course = courseRepository.findById(id).orElseThrow();
		course.setDeletedBy(deletedBy);
		course.setDeletedDate(LocalDate.now());
		courseRepository.save(course);
	}
}
