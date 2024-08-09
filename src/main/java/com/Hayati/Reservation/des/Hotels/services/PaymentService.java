package com.Hayati.Reservation.des.Hotels.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Hayati.Reservation.des.Hotels.dto.PaymentDTO;
import com.Hayati.Reservation.des.Hotels.entity.Payment;
import com.Hayati.Reservation.des.Hotels.entity.PaymentMethod;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaiementRepositoriy;
import com.Hayati.Reservation.des.Hotels.repositoriy.PaymentMethodRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaiementRepositoriy paiementRepositoriy;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public Payment createPayment(PaymentDTO paymentDTO) {
        logger.info("Creating payment with DTO: {}", paymentDTO);

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDTO.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setReference(paymentDTO.getReference());
        payment.setPaymentMethod(paymentMethod);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paiementRepositoriy.save(payment);
        logger.info("Payment created: {}", savedPayment);

        return savedPayment;
    }

    public Payment getPaymentById(Long id) {
        logger.info("Getting payment by id: {}", id);
        return paiementRepositoriy.findById(id).orElseThrow(() -> {
            logger.error("Payment not found with id: {}", id);
            return new RuntimeException("Payment not found");
        });
    }

    public List<Payment> getAllPayments() {
        logger.info("Getting all payments");
        return paiementRepositoriy.findAll();
    }

    public Payment updatePayment(Long id, PaymentDTO paymentDTO) {
        logger.info("Updating payment with id: {}", id);
        Payment existingPayment = getPaymentById(id);

        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentDTO.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setReference(paymentDTO.getReference());
        existingPayment.setPaymentMethod(paymentMethod);

        Payment updatedPayment = paiementRepositoriy.save(existingPayment);
        logger.info("Payment updated: {}", updatedPayment);

        return updatedPayment;
    }

    public void deletePayment(Long id) {
        logger.info("Deleting payment with id: {}", id);
        paiementRepositoriy.deleteById(id);
    }
}
