package com.example.tutoring.dmain.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tutoring.dmain.calendar.entity.CalendarEventEntity;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEventEntity, Long>, CalendarEventCustomRepository {
}
