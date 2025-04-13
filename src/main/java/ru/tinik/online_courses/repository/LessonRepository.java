package ru.tinik.online_courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.tinik.online_courses.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
