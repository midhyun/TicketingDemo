package com.example.demo.service;

import com.example.demo.domain.Event;
import com.example.demo.dto.EventRegistrationDto;
import com.example.demo.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    @Override
    public Event registerEvent(EventRegistrationDto eventDto) {
        Event event = Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .location(eventDto.getLocation())
                .totalTickets(eventDto.getTotalTickets())
                .ticketPrice(eventDto.getTicketPrice())
                .build();

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }

}
