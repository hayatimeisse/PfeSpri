package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Chambre")
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_cham;


    @Column(nullable = false)
    private int capacite;

    @Column(nullable = false)
    private float prixJour;

    @Column(nullable = false)
    private String disponibilites;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl; 



    @ManyToOne
    @JoinColumn(name = "suite_id")
    private Suite suite;
}
