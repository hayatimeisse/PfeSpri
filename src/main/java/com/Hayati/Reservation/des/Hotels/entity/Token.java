package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean invalid; // Marqueur pour invalider le token

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
