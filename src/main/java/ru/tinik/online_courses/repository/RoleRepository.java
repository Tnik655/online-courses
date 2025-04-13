package ru.tinik.online_courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.tinik.online_courses.enums.RoleType;
import ru.tinik.online_courses.models.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByType(RoleType type);
}
