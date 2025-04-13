package ru.tinik.online_courses.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course")
@NamedEntityGraph(name = "CourseNoJoins")
@NamedEntityGraph(name = "CourseJoinAuthor", attributeNodes = {@NamedAttributeNode("author")})
@Setter
@Getter
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	@NotBlank(message = "Course title has to be filled")
	private String title;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
//	@NotBlank(message = "Course authore has to be filled")
	private UserPrincipal author;

	@Column(name = "passing_time")
	private Short passingTime;

	@Column(name = "created_date")
	private LocalDate createdDate;

	@OneToOne
	@JoinColumn(name = "created_by_id")
	private UserPrincipal createdBy;

	@Column(name = "deleted_date")
	private LocalDate deletedDate;

	@OneToOne
	@JoinColumn(name = "deleted_by_id")
	private UserPrincipal deletedBy;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "link_course_category", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@JsonIgnore
	private Set<CourseCategory> categories = new HashSet<>();

	@OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CourseModule> modules = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "link_course_tag", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@JsonIgnore
	private Set<CourseTag> tags = new HashSet<>();

	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	private Set<UserPrincipal> users = new HashSet<>();

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
		Course course = (Course) obj;
		return id != null && id.equals(course.id);
	}

	@Transactional
	public void addCategory(CourseCategory category) {
		this.categories.add(category);
		category.getCourses().add(this);
	}

	@Transactional
	public void removeCategory(CourseCategory category) {
		this.categories.remove(category);
		category.getCourses().remove(this);
	}
}
