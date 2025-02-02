package com.example.tutoring.dmain.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.example.tutoring.dmain.calendar.dto.CalendarEventDto;
import com.example.tutoring.dmain.calendar.dto.CreateEventDto;
import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.students.entity.LessonEntity;
import com.example.tutoring.dmain.user.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CalendarEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "teacherId", target = "teacherUser", qualifiedByName = "idToUser")
    @Mapping(target = "lesson", ignore = true)
    CalendarEventEntity toEntity(Long teacherId, CreateEventDto.Request dto);

    @Mapping(source = "teacherUser.id", target = "teacherId")
    CalendarEventDto toDto(CalendarEventEntity savedCalendarEvent);

    @Named("idToUser")
    default UserEntity idToUser(Long id) {
        if (id == null) {
            return null;
        }
        UserEntity user = UserEntity.builder()
            .id(id)
            .build();
        return user;
    }

    @Named("idToStudent")
    default LessonEntity idToStudent(Long id) {
        if (id == null) {
            return null;
        }
        LessonEntity student = LessonEntity.builder()
            .id(id)
            .build();
        return student;
    }
}
