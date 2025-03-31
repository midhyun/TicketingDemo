package com.example.demo.service;

import com.example.demo.domain.Reservation;
import com.example.demo.dto.ReservationRequestDto;

import java.util.List;

public interface ReservationService {

    Reservation reserveTickets(ReservationRequestDto request);

    Reservation processReservationFromQueue(ReservationRequestDto requestDto);

    List<Reservation> getReservationByEvent(Long eventId);
}
