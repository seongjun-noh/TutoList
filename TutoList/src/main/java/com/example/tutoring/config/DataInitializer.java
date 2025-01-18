package com.example.tutoring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.tutoring.dmain.user.entity.UserEntity;
import com.example.tutoring.dmain.user.enums.UserRole;
import com.example.tutoring.dmain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        String encode = passwordEncoder.encode("admin12345!");

        return args -> {
            // 데이터베이스에 사용자가 존재하지 않을 경우 기본 사용자 생성
            if (userRepository.count() == 0) {
                UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .password(encode)
                    .name("ADMIN")
                    .role(UserRole.ADMIN)
                    .phone("010-1111-1111")
                    .email("example@example.org")
                    .build();

                userRepository.save(admin);
            }
        };
    }
}