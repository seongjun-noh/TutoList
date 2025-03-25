package com.tutolist.domain.student.entity;

import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.tutolist.common.domain.BaseEntity;
import com.tutolist.domain.relation.StudentSubjectEntity;
import com.tutolist.domain.user.entity.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class StudentEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String schoolGrade;  // "중학교 1학년" 형식으로 저장
    
    @Column
    private String contact;
    
    @Column
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 50)
    private List<StudentSubjectEntity> studentSubjects;

    public void updateSchoolGrade(String schoolGrade) {
        this.schoolGrade = schoolGrade;
    }
    
    public void updateContact(String contact) {
        this.contact = contact;
    }
    
    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateStudentSubjects(List<StudentSubjectEntity> studentSubjects) {
        this.studentSubjects = studentSubjects;
    }
}