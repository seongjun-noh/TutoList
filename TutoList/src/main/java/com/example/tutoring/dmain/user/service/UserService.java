package com.example.tutoring.dmain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tutoring.cmmn.error.enums.ErrorMessage;
import com.example.tutoring.cmmn.error.exception.CustomException;
import com.example.tutoring.dmain.user.dto.UserSignupDto;
import com.example.tutoring.dmain.user.dto.UsernameExistsDto;
import com.example.tutoring.dmain.user.entity.UserEntity;
import com.example.tutoring.dmain.user.enums.UserRole;
import com.example.tutoring.dmain.user.repository.UserRepository;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

	public void signup(UserSignupDto.Request info) {
		// if (info.getRole() == UserRole.ADMIN) {
		// 	throw new CustomException(ErrorMessage.ERROR_INVALID_ROLE);
		// }

		boolean isExistsUsername = userRepository.existsByUsername(info.getUsername());
		if (isExistsUsername) {
			throw new CustomException(ErrorMessage.ERROR_EXISTS_USERNAME);
		}

		// 비밀번호를 인코딩
		String encodedPassword = passwordEncoder.encode(info.getPassword());

		UserEntity user = UserEntity.builder()
			.username(info.getUsername())
			.password(encodedPassword)
			.name(info.getName())
			.email(info.getEmail())
			.role(UserRole.TEACHER)
			.build();

		userRepository.save(user);
	}

	public UsernameExistsDto.Response existsUsername(UsernameExistsDto.Request requestBody) {
		boolean exists = userRepository.existsByUsername(requestBody.getUsername());

		return UsernameExistsDto.Response.builder()
			.exists(exists)
			.build();
	}
}
