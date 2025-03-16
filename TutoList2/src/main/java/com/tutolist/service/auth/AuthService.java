package com.tutolist.service.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutolist.api.auth.dto.request.LoginRequest;
import com.tutolist.api.auth.dto.request.RegisterRequest;
import com.tutolist.api.auth.dto.request.TokenRefreshRequest;
import com.tutolist.api.auth.dto.response.UserInfoResponse;
import com.tutolist.common.enums.UserRole;
import com.tutolist.common.error.exception.BadRequestException;
import com.tutolist.common.error.exception.ConflictException;
import com.tutolist.domain.user.entity.UserEntity;
import com.tutolist.domain.user.repository.UserRepository;
import com.tutolist.security.details.PrincipalDetails;
import com.tutolist.security.jwt.JwtDto;
import com.tutolist.security.jwt.JwtUtil;
import com.tutolist.service.redis.RedisService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final RedisService redisService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void register(RegisterRequest request) {
		log.info("Registering new user with email: {}", request.email());
		
		validateEmailUniqueness(request.email());
		validatePasswordMatch(request.password(), request.passwordCheck());
		
		String encodedPassword = passwordEncoder.encode(request.password());
		UserEntity newUser = createUserEntity(request, encodedPassword);
		
		userRepository.save(newUser);
		log.info("User registered successfully: {}", request.email());
	}

	@Transactional(readOnly = true)
	public JwtDto login(LoginRequest request) {
		log.info("Login attempt for user: {}", request.email());
		
		UserEntity user = findUserByEmail(request.email());
		validatePassword(request.password(), user.getPassword());
		
		PrincipalDetails principalDetails = createPrincipalDetails(user);
		JwtDto tokens = generateTokens(principalDetails);
		
		saveRefreshToken(user.getEmail(), tokens.refreshToken());
		log.info("User logged in successfully: {}", request.email());
		
		return tokens;
	}

	public void logout(String email, String accessToken, String refreshToken) {
		log.info("Logging out user: {}", email);
		
		Claims accessTokenClaims = jwtUtil.parseToken(accessToken);
		Claims refreshTokenClaims = jwtUtil.parseToken(refreshToken);
		
		saveTokensToBlacklist(accessToken, refreshToken, accessTokenClaims, refreshTokenClaims);
		redisService.deleteRefreshToken(email);
		
		log.info("User logged out successfully: {}", email);
	}

	@Transactional(readOnly = true)
	public JwtDto refresh(TokenRefreshRequest request) {
		log.info("Token refresh requested");
		
		String oldRefreshToken = request.refreshToken();
		Claims oldRefreshTokenClaims = validateRefreshToken(oldRefreshToken);
		
		String email = jwtUtil.getUsername(oldRefreshTokenClaims);
		validateStoredRefreshToken(email, oldRefreshToken);
		
		UserEntity user = findUserByEmail(email);
		PrincipalDetails principalDetails = createPrincipalDetails(user);
		
		JwtDto newTokens = generateTokens(principalDetails);
		updateTokens(email, oldRefreshToken, oldRefreshTokenClaims, newTokens.refreshToken());
		
		log.info("Token refreshed successfully for user: {}", email);
		return newTokens;
	}

	@Transactional(readOnly = true)
	public UserInfoResponse getCurrentUser(Long userId) {
		log.info("Fetching user info for user ID: {}", userId);
		UserEntity user = findUserById(userId);
		return createUserInfoResponse(user);
	}

	private void validateEmailUniqueness(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new ConflictException("이미 사용 중인 이메일입니다.");
		}
	}

	private void validatePasswordMatch(String password, String passwordCheck) {
		if (!password.equals(passwordCheck)) {
			throw new BadRequestException("비밀번호가 일치하지 않습니다.");
		}
	}

	private UserEntity createUserEntity(RegisterRequest request, String encodedPassword) {
		return UserEntity.builder()
			.email(request.email())
			.password(encodedPassword)
			.name(request.name())
			.role(UserRole.USER)
			.build();
	}

	private UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

	private void validatePassword(String inputPassword, String storedPassword) {
		if (!passwordEncoder.matches(inputPassword, storedPassword)) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}
	}

	private PrincipalDetails createPrincipalDetails(UserEntity user) {
		return PrincipalDetails.builder()
			.id(user.getId())
			.role(user.getRole())
			.email(user.getEmail())
			.build();
	}

	private JwtDto generateTokens(PrincipalDetails principalDetails) {
		String accessToken = jwtUtil.generateAccessToken(principalDetails);
		String refreshToken = jwtUtil.generateRefreshToken(principalDetails);
		return new JwtDto(accessToken, refreshToken);
	}

	private void saveRefreshToken(String email, String refreshToken) {
		redisService.saveRefreshToken(email, refreshToken, jwtUtil.getREFRESH_TOKEN_EXPIRATION());
	}

	private void saveTokensToBlacklist(String accessToken, String refreshToken, 
			Claims accessTokenClaims, Claims refreshTokenClaims) {
		redisService.saveBlackList(accessToken, jwtUtil.getRemainingExpirationMs(accessTokenClaims));
		redisService.saveBlackList(refreshToken, jwtUtil.getRemainingExpirationMs(refreshTokenClaims));
	}

	private Claims validateRefreshToken(String refreshToken) {
		Claims claims = jwtUtil.parseToken(refreshToken);
		if (claims == null || !jwtUtil.isRefreshToken(claims) || redisService.isBlacklisted(refreshToken)) {
			throw new BadRequestException("유효하지 않은 리프레시 토큰입니다.");
		}
		return claims;
	}

	private void validateStoredRefreshToken(String email, String refreshToken) {
		String storedRefreshToken = redisService.getRefreshToken(email);
		if (storedRefreshToken == null || !refreshToken.equals(storedRefreshToken)) {
			throw new BadRequestException("만료되었거나 유효하지 않은 리프레시 토큰입니다.");
		}
	}

	private void updateTokens(String email, String oldRefreshToken, Claims oldRefreshTokenClaims, String newRefreshToken) {
		saveRefreshToken(email, newRefreshToken);
		redisService.saveBlackList(oldRefreshToken, jwtUtil.getRemainingExpirationMs(oldRefreshTokenClaims));
	}

	private UserEntity findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

	private UserInfoResponse createUserInfoResponse(UserEntity user) {
		return new UserInfoResponse(
			user.getId(),
			user.getEmail(),
			user.getName(),
			user.getRole(),
			user.getProfileImageUrl()
		);
	}
}
