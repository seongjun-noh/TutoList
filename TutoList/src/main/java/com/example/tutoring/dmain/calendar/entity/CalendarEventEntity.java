package com.example.tutoring.dmain.calendar.entity;

import java.time.LocalDateTime;

import com.example.tutoring.cmmn.entity.BaseEntity;
import com.example.tutoring.dmain.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "CALENDAR_EVENT")
public class CalendarEventEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDateTime start;

	private LocalDateTime end;

	@Column(length = 300) // 길이가 긴 문자열을 저장
	private String description;

	@Column(nullable = false)
	private Boolean allDay = false;

	@Column(name = "RRULE", length = 200) // rrule 문자열로 저장
	private String rrule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, updatable = false)
	private UserEntity user;
}