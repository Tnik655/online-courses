package ru.tinik.online_courses.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = "UserNoJoins")
@NamedEntityGraph(name = "UserWithRole")
@Setter
@Getter
public class UserPrincipal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String username;

	@Column
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column
	private String email;

	// TODO Разберись с хранением фото
	@Column(name = "profile_picture")
	private String profilePicture;

	@Column(name = "registration_date")
	private LocalDate registrationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	@JsonIgnore
	private Role role;

	@ManyToMany
	@JoinTable(name = "link_users_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	@JsonIgnore
	private Set<Course> courses = new HashSet<>();

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		UserPrincipal user = (UserPrincipal) obj;
		return id != null && id.equals(user.id);
	}

	@Transactional
	public void addCourse(Course course) {
		courses.add(course);
		course.getUsers().add(this);
	}

	@Transactional
	public void removeCourse(Course course) {
		courses.remove(course);
		course.getUsers().remove(this);
	}
}
