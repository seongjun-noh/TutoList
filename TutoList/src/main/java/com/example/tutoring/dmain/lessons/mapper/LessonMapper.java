package com.example.tutoring.dmain.lessons.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.tutoring.dmain.lessons.dto.LessonDto;
import com.example.tutoring.dmain.lessons.entity.LessonEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface LessonMapper {

	@Mapping(source = "teacherUser.id", target = "teacherUserId")
	LessonDto toDto(LessonEntity lessonEntity);
}
