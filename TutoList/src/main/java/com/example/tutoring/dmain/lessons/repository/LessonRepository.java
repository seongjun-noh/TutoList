package com.example.tutoring.dmain.lessons.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tutoring.dmain.lessons.entity.LessonEntity;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
	List<LessonEntity> findByTeacherUserId(Long teacherUserId);

	Optional<LessonEntity> findByTeacherUserIdAndId(Long teacherUserId, Long studentId);
}