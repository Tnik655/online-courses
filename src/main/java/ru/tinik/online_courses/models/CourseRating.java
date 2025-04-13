package ru.tinik.online_courses.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
public class CourseRating {

	@EmbeddedId
	private CourseRatingPK ratingPK;

	@Column
	private Integer rating;
}
