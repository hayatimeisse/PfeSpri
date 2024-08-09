package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class PaymentDetails {
    private String paymentReference;
    private LocalDateTime date;
    private String paymentMode;
    private String orderReference;
    private Double amount;
    private String sessionReference;
    private String customer;
}
