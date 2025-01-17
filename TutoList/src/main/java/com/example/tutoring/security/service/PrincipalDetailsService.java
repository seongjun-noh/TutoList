package com.example.tutoring.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tutoring.dmain.user.entity.UserEntity;
import com.example.tutoring.dmain.user.repository.UserRepository;
import com.example.tutoring.security.dto.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("유저 정보를 찾지 못했습니다."));

		PrincipalDetails principalDetails = new PrincipalDetails(
			user.getUsername(),
			user.getPassword(),
			user.getRole()
		);

		return principalDetails;
	}

}