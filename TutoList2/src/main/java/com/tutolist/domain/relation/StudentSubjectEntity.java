package com.tutolist.domain.relation;

import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.subject.entity.SubjectEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
	name = "student_subject",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"student_id", "subject_id"})
	}
)
public class StudentSubjectEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
  	private StudentEntity student;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
  	private SubjectEntity subject;
}
