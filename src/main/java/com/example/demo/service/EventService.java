package com.example.demo.service;

import com.example.demo.domain.Event;
import com.example.demo.dto.EventRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event registerEvent(EventRegistrationDto eventDto);
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long eventId);
}
