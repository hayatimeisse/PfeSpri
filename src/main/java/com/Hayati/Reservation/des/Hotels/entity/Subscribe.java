package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("SUBSCRIBE")
public class Subscribe extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_sub;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
