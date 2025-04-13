package ru.tinik.online_courses.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "module")
@Getter
@Setter
public class CourseModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String description;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@OneToOne
	@JoinColumn(name = "created_by_id")
	private UserPrincipal createdBy;

	@Column(name = "changed_date")
	private LocalDate changedDate;

	@OneToOne
	@JoinColumn(name = "changed_by_id")
	private UserPrincipal changedBy;

	@Column(name = "deleted_date")
	private LocalDate deletedDate;

	@OneToOne
	@JoinColumn(name = "deleted_by_id")
	private UserPrincipal deletedBy;

	@OneToMany(mappedBy = "module")
	private Set<Lesson> lessons = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	@JsonIgnore
	private Course course;
}
