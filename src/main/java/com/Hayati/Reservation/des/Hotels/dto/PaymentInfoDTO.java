package com.Hayati.Reservation.des.Hotels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfoDTO {
    private Long id;
    private String paymentReference;
    private LocalDateTime date;
    private String paymentMode;
    private String orderReference;
    private Double amount;

}
