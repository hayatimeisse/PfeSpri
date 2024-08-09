package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.Hayati.Reservation.des.Hotels.enumeration.PaymentType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String nom;

    // Validating the code to be a maximum of 6 digits
    @Column(length = 6)
    private long code;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private boolean active = false;
    private LocalDateTime createdAt;
    private String logoImagePath;
}
