package com.example.tutoring.dmain.user.entity;

import org.hibernate.annotations.Comment;

import com.example.tutoring.cmmn.entity.BaseEntity;
import com.example.tutoring.dmain.user.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "USERS")
@Comment("유저 정보")
public class UserEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "USERNAME", nullable = false, updatable = false, length = 20)
	@Comment("로그인 아이디")
	private String username;

	@Column(name = "PASSWORD")
	@Comment("비밀번호")
	private String password;

	@Column(name = "NAME", nullable = false, length = 20)
	@Comment("이름")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false, length = 15)
	@Comment("권한")
	private UserRole role;

	@Column(name = "PHONE", length = 20)
	@Comment("전화번호")
	private String phone;

	@Column(name = "EMAIL", length = 30)
	@Comment("이메일")
	private String email;
}
