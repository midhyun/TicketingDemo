package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationRequestDto {

    @NotNull(message = "이벤트 ID는 필수 입력 항목입니다.")
    private Long eventId;

    @Min(value = 1, message = "예약할 티켓 수는 최소 1 이상이어야 합니다.")
    @Max(value = 4, message = "예약할 티켓 수는 최대 4까지 가능합니다.")
    private int ticketCount;
}
