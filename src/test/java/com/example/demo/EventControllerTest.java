package com.example.demo;

import com.example.demo.controller.EventController;
import com.example.demo.domain.Event;
import com.example.demo.dto.EventRegistrationDto;
import com.example.demo.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EventController.class)
@Import(EventControllerTest.TestConfig.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventService eventService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public EventService eventService() {
            return Mockito.mock(EventService.class);
        }
    }

    @Test
    @WithMockUser
    public void testRegisterEvent() throws Exception {

        // 테스트용 DTO 생성
        EventRegistrationDto dto = new EventRegistrationDto();
        dto.setTitle("Test Event");
        dto.setDescription("Test Description");
        dto.setStartTime(LocalDateTime.now().plusMinutes(5));
        dto.setEndTime(LocalDateTime.now().plusHours(1));
        dto.setLocation("서울");
        dto.setTotalTickets(100);
        dto.setTicketPrice(50.0);

        // 예상되는 저장된 Event 엔티티 생성
        Event savedEvent = new Event();
        savedEvent.setId(1L);
        savedEvent.setTitle(dto.getTitle());
        savedEvent.setDescription(dto.getDescription());
        savedEvent.setStartTime(dto.getStartTime());
        savedEvent.setEndTime(dto.getEndTime());
        savedEvent.setLocation(dto.getLocation());
        savedEvent.setTotalTickets(dto.getTotalTickets());
        savedEvent.setTicketPrice(dto.getTicketPrice());

        // EventService 의 registerEvent 메서드를 모킹
        Mockito.when(eventService.registerEvent(Mockito.any(EventRegistrationDto.class)))
                .thenReturn(savedEvent);

        // POST /api/events 요청을 수행하고 응답을 검증
        mockMvc.perform(post("/api/events")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(savedEvent.getId()))
            .andExpect(jsonPath("$.title").value(savedEvent.getTitle()));

    }
}
