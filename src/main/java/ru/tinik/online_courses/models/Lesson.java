package ru.tinik.online_courses.models;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lesson")
@Getter
@Setter
public class Lesson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String description;

	@Column
	private String title;

	@Column
	private String text;

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

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "module_id", nullable = false)
	@JsonIgnore
	private CourseModule module;

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
		Lesson other = (Lesson) obj;
		return Objects.equals(id, other.id);
	}

}
