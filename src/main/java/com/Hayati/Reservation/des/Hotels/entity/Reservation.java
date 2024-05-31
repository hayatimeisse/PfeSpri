package com.Hayati.Reservation.des.Hotels.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_res;

    private Date dateSejour;
    private float nombreChambre;
    private String statutReser;
    private float montantTotal;
    private Date datefin;
    
    @OneToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;
}