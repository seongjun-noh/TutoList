package com.tutolist.service.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutolist.api.student.dto.request.StudentCreateRequest;
import com.tutolist.api.student.dto.response.StudentResponse;
import com.tutolist.common.error.exception.NotFoundException;
import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.student.repository.StudentRepository;
import com.tutolist.domain.user.entity.UserEntity;
import com.tutolist.domain.user.repository.UserRepository;
import com.tutolist.service.student.mapper.StudentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponse createStudent(Long teacherId, StudentCreateRequest request) {
        log.info("Creating student with name: {}", request.name());
        
        UserEntity teacher = findUserById(teacherId);
        UserEntity user = request.userId() != null ? findUserById(request.userId()) : null;
        
        StudentEntity student = studentMapper.toEntity(request, teacher, user);
        StudentEntity savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getId());
        
        return studentMapper.toResponse(savedStudent);
    }

    public Page<StudentResponse> getStudents(Long teacherId, Pageable pageable) {
        log.info("Retrieving students for teacher: {}", teacherId);
        
        UserEntity teacher = findUserById(teacherId);

        return studentRepository.findAllByTeacher(teacher, pageable)
            .map(studentMapper::toResponse);
    }
    
    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }
} 