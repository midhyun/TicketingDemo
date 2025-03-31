package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRegistrationDto {

    @NotBlank(message = "이벤트 제목은 필수 입력 항목입니다.")
    private String title;

    private String description;

    @NotNull(message = "이벤트 시작 시간은 필수 입력 항목입니다.")
    private LocalDateTime startTime;

    @NotNull(message = "이벤트 종료 시간은 필수 입력 항목입니다.")
    private LocalDateTime endTime;

    private String location;

    @Min(value = 1, message = "최소 1개 이상의 티켓을 등록해야 합니다.")
    private int totalTickets;

    @Min(value = 0, message = "티켓 가격은 0원 이상이어야 합니다.")
    private double ticketPrice;
}
