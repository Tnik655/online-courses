package ru.tinik.online_courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.tinik.online_courses.models.Course;
import ru.tinik.online_courses.models.CourseModule;
import java.util.List;


@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long>{
	List<CourseModule> findByCourse(Course course);
}
