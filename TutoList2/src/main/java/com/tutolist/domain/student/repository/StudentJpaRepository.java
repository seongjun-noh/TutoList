package com.tutolist.domain.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.user.entity.UserEntity;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long>, StudentRepository {

	@Query("select s from StudentEntity s left join fetch s.studentSubjects where s.teacher = :teacher")
  	Page<StudentEntity> findAllByTeacher(UserEntity teacher, Pageable pageable);

  	@Query("select s from StudentEntity s left join fetch s.studentSubjects where s.teacher = :teacher and s.id = :studentId")
	Optional<StudentEntity> findByTeacherAndId(UserEntity teacher, Long studentId);
}