package com.example.demo.service;

import com.example.demo.dto.ReservationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationQueueConsumer {

    private final ReservationService reservationService;

    public void processReservation(ReservationRequestDto requestDto) {
        // 예약 요청을 처리하는 로직
        try{
            reservationService.processReservationFromQueue(requestDto);
        } catch (Exception e) {
            // 예외 처리 로직
            e.printStackTrace();
        }
    }
}
