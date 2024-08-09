package com.Hayati.Reservation.des.Hotels.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankilyPaymentRequest {
    private String clientPhone;
    private String passcode;
    private String operationId;
    private String amount;
    private String language;
}
