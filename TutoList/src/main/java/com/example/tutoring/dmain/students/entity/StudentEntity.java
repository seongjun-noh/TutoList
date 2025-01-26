package com.example.tutoring.dmain.students.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StudentEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("ID")
	private Long id;

	@Column(nullable = false, length = 20)
	@Comment("이름")
	private String name;
}
