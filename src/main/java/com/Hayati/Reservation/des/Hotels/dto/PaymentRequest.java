package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
    private String clientPhone;
    private String passcode;
    private String operationId;
    private String amount;
    private String language;
}
