package com.example.demo.service;

import com.example.demo.domain.Event;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.User;
import com.example.demo.dto.ReservationRequestDto;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final ReservationQueuePublisher reservationQueuePublisher;

    @Override
    public Reservation reserveTickets(ReservationRequestDto request) {
        reservationQueuePublisher.publishReservationRequest(request);
        throw new RuntimeException("예약 요청이 큐에 추가되었습니다. 대기열을 처리하는 중입니다.");
    }

    @Override
    @Transactional
    public Reservation processReservationFromQueue(ReservationRequestDto request) {

        Event event = eventRepository.findByIdForUpdate(request.getEventId())
                .orElseThrow(() -> new RuntimeException("이벤트를 찾을 수 없습니다."));

        int reservedTickets = reservationRepository.countByEventId(event.getId());
        int availableTickets = event.getTotalTickets() - reservedTickets;

        if (availableTickets < request.getTicketCount()) {
            throw new RuntimeException("예약할 수 있는 좌석이 부족합니다.");
        }

        User user = getAuthenticatedUser();

        Reservation reservation = new Reservation();
        reservation.setEvent(event);
        reservation.setUser(user);
        reservation.setTicketCount(request.getTicketCount());
        reservation.setStatus("SUCCESS");
        reservation.setReservationTime(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationByEvent(Long eventId) {
        return reservationRepository.findByEventId(eventId);
    }

    private User getAuthenticatedUser() {
        // 인증된 사용자 정보를 가져오는 로직
        // 예를 들어, SecurityContextHolder를 사용하여 현재 인증된 사용자를 가져올 수 있습니다.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        throw new RuntimeException("인증된 사용자가 아닙니다.");
    }
}
