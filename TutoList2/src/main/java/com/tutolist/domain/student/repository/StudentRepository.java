package com.tutolist.domain.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.user.entity.UserEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  Page<StudentEntity> findAllByTeacher(UserEntity user, Pageable pageable);
} 