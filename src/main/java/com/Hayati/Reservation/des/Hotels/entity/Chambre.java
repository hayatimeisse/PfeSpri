package com.Hayati.Reservation.des.Hotels.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private String name;

    @Column(nullable = false)
    private int capacite;
    

    @Column(nullable = false)
    private float prixJour;

    @Column(nullable = false)
    private boolean disponibilites;
    

    @Column(nullable = false, length = 1000)  // Adjust length based on DB schema change
    private String description;
    
    @Column(nullable = false, length = 500)  // Adjust length based on DB schema change
    private String imageUrl;
    


    @ManyToOne
    @JoinColumn(name = "suite_id")
    private Suite suite;
       
}
