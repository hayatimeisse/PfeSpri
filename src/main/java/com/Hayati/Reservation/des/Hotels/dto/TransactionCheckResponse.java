package com.Hayati.Reservation.des.Hotels.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCheckResponse {
    private String errorCode;
    private String errorMessage;
    private String transactionId;
    private String status;
}
