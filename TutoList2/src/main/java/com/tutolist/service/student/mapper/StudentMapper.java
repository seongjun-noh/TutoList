package com.tutolist.service.student.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.tutolist.api.student.dto.request.StudentCreateRequest;
import com.tutolist.api.student.dto.response.StudentResponse;
import com.tutolist.domain.student.entity.StudentEntity;
import com.tutolist.domain.user.entity.UserEntity;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "schoolGrade", source = "request.schoolGrade")
    @Mapping(target = "contact", source = "request.contact")
    @Mapping(target = "memo", source = "request.memo")
    @Mapping(target = "user", source = "user")
    StudentEntity toEntity(StudentCreateRequest request, UserEntity teacher, UserEntity user);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "schoolGrade", source = "schoolGrade")
    @Mapping(target = "contact", source = "contact")
    @Mapping(target = "memo", source = "memo")
    @Mapping(target = "userId", source = "user", qualifiedByName = "extractUserId")
    StudentResponse toResponse(StudentEntity student);
    
    @Named("extractUserId")
    default Long extractUserId(UserEntity user) {
        return user != null ? user.getId() : null;
    }
} 