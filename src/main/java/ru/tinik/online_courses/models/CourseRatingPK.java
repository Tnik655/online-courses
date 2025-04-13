package ru.tinik.online_courses.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CourseRatingPK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private UserPrincipal user;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@JsonIgnore
	private Course course;
}
