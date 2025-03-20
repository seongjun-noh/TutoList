package com.tutolist.domain.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutolist.domain.subject.entity.SubjectEntity;

public interface SubjectJpaRepository extends JpaRepository<SubjectEntity, Long>, SubjectRepository {
}
