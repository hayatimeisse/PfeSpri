package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.BankilyPaymentRequest;
import com.Hayati.Reservation.des.Hotels.dto.PaymentResponse;
import com.Hayati.Reservation.des.Hotels.exceptions.PaymentException;

public interface BankilyService {
    PaymentResponse makePayment(BankilyPaymentRequest request) throws PaymentException;

}
