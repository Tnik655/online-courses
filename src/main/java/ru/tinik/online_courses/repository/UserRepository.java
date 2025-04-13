package ru.tinik.online_courses.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.tinik.online_courses.models.UserPrincipal;

public interface UserRepository extends JpaRepository<UserPrincipal, Long> {

	@Query("""
			FROM UserPrincipal u
			WHERE u.id not in (
				SELECT u.id
				FROM UserPrincipal u
				LEFT JOIN u.courses c
				WHERE c.id = :courseId)
			""")
	List<UserPrincipal> findUsersNotAssignedToCourse(@Param("courseId") long courseId);
	
	Optional<UserPrincipal> findByUsername(String username);
}
