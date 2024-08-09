package com.Hayati.Reservation.des.Hotels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class PaymentStateDto {
    private Long sessionId;
    private String paymentType;
    private Double amount;
}
