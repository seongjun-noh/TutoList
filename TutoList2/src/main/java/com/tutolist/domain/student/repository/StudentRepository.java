package com.tutolist.domain.student.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.user.entity.UserEntity;

@Repository
public interface StudentRepository {

  	Page<StudentEntity> findAllByTeacher(UserEntity user, Pageable pageable);

	Optional<StudentEntity> findByTeacherAndId(UserEntity teacher, Long studentId);

	StudentEntity save(StudentEntity student);
}