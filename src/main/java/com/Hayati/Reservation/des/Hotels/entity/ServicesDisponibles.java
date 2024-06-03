package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Services")
public class ServicesDisponibles {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ser;

    
    @Column(nullable = false)
    private String Type;
    
}
