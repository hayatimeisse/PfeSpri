package com.Hayati.Reservation.des.Hotels.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class ServicesDisponibles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ser;

    @Column(nullable = false)
    private String type;
  
}
