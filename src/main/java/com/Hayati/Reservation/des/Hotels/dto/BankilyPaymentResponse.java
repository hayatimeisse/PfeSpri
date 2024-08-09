package com.Hayati.Reservation.des.Hotels.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankilyPaymentResponse {
    private String errorCode;
    private String errorMessage;
    private String transactionId;
}
