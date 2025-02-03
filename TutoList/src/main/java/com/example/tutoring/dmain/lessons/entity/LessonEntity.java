package com.example.tutoring.dmain.lessons.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.Comment;

import com.example.tutoring.cmmn.entity.BaseEntity;
import com.example.tutoring.dmain.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "lesson")
public class LessonEntity extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("ID")
	private Long id;

	@Column(nullable = false, length = 20)
	@Comment("이름")
	private String name;

	@JoinColumn(name = "teacherUserId", nullable = false)
	@ManyToOne
	@Comment("선생 유저 ID")
	private UserEntity teacherUser;

	private String subject;

	private String memo;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalTime startTime;

	@Column(nullable = false)
	private Integer duration;

	@Column(nullable = false)
	private String rrule;
}
