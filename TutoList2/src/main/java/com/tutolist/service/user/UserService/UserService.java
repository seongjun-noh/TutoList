package com.tutolist.service.user.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tutolist.api.user.dto.request.UserUpdateRequest;
import com.tutolist.common.error.exception.NotFoundException;
import com.tutolist.domain.user.entity.UserEntity;
import com.tutolist.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	// private final FileService fileService;

	@Transactional
	public void updateUser(Long userId, UserUpdateRequest request, MultipartFile profileImage) {
		log.info("Updating user information for user ID: {}", userId);
		
		UserEntity user = findUserById(userId);
		updateUserInformation(user, request);
		
		if (profileImage != null && !profileImage.isEmpty()) {
			updateProfileImage(user, profileImage);
		}
		
		userRepository.save(user);
		log.info("User information updated successfully for user ID: {}", userId);
	}

	private UserEntity findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
	}

	private void updateUserInformation(UserEntity user, UserUpdateRequest request) {
		user.updateName(request.name());
		// 추가적인 사용자 정보 업데이트 로직이 필요한 경우 여기에 구현
	}

	private void updateProfileImage(UserEntity user, MultipartFile profileImage) {
		// TODO: 프로필 이미지 업데이트 로직 구현
		// String imageUrl = fileService.uploadFile(profileImage);
		// user.updateProfileImage(imageUrl);
	}
}
