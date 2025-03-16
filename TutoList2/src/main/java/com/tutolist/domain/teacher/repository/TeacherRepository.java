package com.tutolist.domain.teacher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutolist.domain.teacher.entity.TeacherEntity;
import com.tutolist.domain.user.entity.UserEntity;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    boolean existsByUser(UserEntity user);
    Optional<TeacherEntity> findByUser(UserEntity user);
} 