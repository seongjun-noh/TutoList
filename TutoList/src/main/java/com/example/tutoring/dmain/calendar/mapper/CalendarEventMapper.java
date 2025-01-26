package com.example.tutoring.dmain.calendar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.tutoring.dmain.calendar.dto.CalendarEventDto;
import com.example.tutoring.dmain.calendar.dto.CreateEventDto;
import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;
import com.example.tutoring.dmain.user.entity.UserEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface CalendarEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user")
    CalendarEventEntity toEntity(UserEntity user, CreateEventDto.Request requestBody);

    @Mapping(source = "user.id", target = "userId")
    CalendarEventDto toDto(CalendarEventEntity savedCalendarEvent);
}
