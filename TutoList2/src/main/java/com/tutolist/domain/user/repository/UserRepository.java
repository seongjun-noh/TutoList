package com.tutolist.domain.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tutolist.domain.user.entity.UserEntity;

@Repository
public interface UserRepository {
	Optional<UserEntity> findById(Long id);

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	UserEntity save(UserEntity newUser);
}
