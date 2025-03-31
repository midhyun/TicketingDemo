package com.example.demo.controller;

import com.example.demo.domain.Event;
import com.example.demo.dto.EventRegistrationDto;
import com.example.demo.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> registerEvent(@Valid @RequestBody EventRegistrationDto eventDto) {
        Event savedEvent = eventService.registerEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Optional<Event> eventOptional = eventService.getEventById(id);
        return eventOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
