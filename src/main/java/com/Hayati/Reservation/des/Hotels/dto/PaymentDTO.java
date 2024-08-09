package com.Hayati.Reservation.des.Hotels.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PaymentDTO {
    @NotNull
    private Double amount;

    @NotNull
    private String reference;

    @NotNull
    private Long paymentMethodId;

    // Getters and Setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
