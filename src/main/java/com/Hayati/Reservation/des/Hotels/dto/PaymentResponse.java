package com.Hayati.Reservation.des.Hotels.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String errorCode;
    private String errorMessage;
    private String transactionId;

}
