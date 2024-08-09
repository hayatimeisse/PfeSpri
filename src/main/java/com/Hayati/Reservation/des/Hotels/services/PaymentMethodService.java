package com.Hayati.Reservation.des.Hotels.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.entity.PaymentMethod;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaymentMethodRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        if (String.valueOf(paymentMethod.getCode()).length() > 6) {
            throw new IllegalArgumentException("Code should be a maximum of 6 digits");
        }
        paymentMethod.setCreatedAt(LocalDateTime.now());
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).orElseThrow(() -> new RuntimeException("PaymentMethod not found"));
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod) {
        if (String.valueOf(paymentMethod.getCode()).length() > 6) {
            throw new IllegalArgumentException("Code should be a maximum of 6 digits");
        }

        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);
        existingPaymentMethod.setReference(paymentMethod.getReference());
        existingPaymentMethod.setNom(paymentMethod.getNom());
        existingPaymentMethod.setCode(paymentMethod.getCode());
        existingPaymentMethod.setPaymentType(paymentMethod.getPaymentType());
        existingPaymentMethod.setActive(paymentMethod.isActive());
        existingPaymentMethod.setLogoImagePath(paymentMethod.getLogoImagePath());
        return paymentMethodRepository.save(existingPaymentMethod);
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
