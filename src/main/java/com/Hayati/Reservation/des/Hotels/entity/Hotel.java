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
@Table(name = "Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_hot;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String emplacement;

    @Column(nullable = false)
    private String evaluation;

    @Column(nullable = false)
    private String localisation;

    @Column(nullable = false)
    private String commentaires;

    @Column(nullable = false)
    private String notifications;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    private Chambre chambre;
}
