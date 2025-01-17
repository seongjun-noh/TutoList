package com.example.tutoring.dmain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tutoring.dmain.user.dto.UserSignupDto;
import com.example.tutoring.dmain.user.entity.UserEntity;
import com.example.tutoring.dmain.user.repository.UserRepository;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private static PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

	public void signup(UserSignupDto.Request info) {
		try {
			// 비밀번호를 인코딩
			String encodedPassword = passwordEncoder.encode(info.getPassword());

			// 전화번호를 E.164형식으로 변환
			Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(info.getPhone(), "KR");
			String e164Phone = phoneUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);

			UserEntity user = UserEntity.builder()
				.username(info.getUsername())
				.password(encodedPassword)
				.name(info.getName())
				.role(info.getRole())
				.phone(e164Phone)
				.email(info.getEmail())
				.build();

			userRepository.save(user);
		} catch (NumberParseException e) {
			throw new IllegalArgumentException("옳바른 전화번호 형식이 아닙니다.");
		}
	}
}
