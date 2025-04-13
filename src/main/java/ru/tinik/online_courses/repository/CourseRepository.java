package ru.tinik.online_courses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.UserPrincipal;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	@EntityGraph(value = "CourseNoJoins")
	@Query(value = "SELECT c FROM Course c")
	List<Course> findAll();
	
	@EntityGraph(value = "CourseJoinAuthor")
	@Query(value = "FROM Course с WHERE с.deletedBy is null")
	List<Course> findWithAuthor();

	Optional<Course> findById(Long id);

	List<Course> findByTitleLike(String prefix);
	
	List<Course> findByAuthor(UserPrincipal author);
	
	@Query(nativeQuery = true, value = "SELECT LASTVAL();")
	Long lastInsertId();

	
}
