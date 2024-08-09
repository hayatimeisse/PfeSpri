package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cardNumber;
    
    private String cardholderName;
    
    private YearMonth expirationDate;
    
    private String cvc;
    
    private String country;
}
