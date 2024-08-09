package com.Hayati.Reservation.des.Hotels.services;

import com.Hayati.Reservation.des.Hotels.dto.BankilyPaymentRequest;
import com.Hayati.Reservation.des.Hotels.dto.BankilyPaymentResponse;

public interface BankilyPaymentService {
    BankilyPaymentResponse makePayment(BankilyPaymentRequest request, String strategyBeanName);

}
