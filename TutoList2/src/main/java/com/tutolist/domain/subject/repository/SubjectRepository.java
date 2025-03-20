package com.tutolist.domain.subject.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tutolist.domain.subject.entity.SubjectEntity;

@Repository
public interface SubjectRepository {
	Optional<SubjectEntity> findByName(String subjectStr);

	SubjectEntity save(SubjectEntity entity);
}
