package com.Hayati.Reservation.des.Hotels.entity;



import java.util.Date;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "Paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_pai;
    @Column(nullable = false)
    private float montant;
    @Column(nullable = false)
    private Date datepaiement;
    @Column(nullable = false)
    private String methodepaiement;

    @OneToOne(mappedBy= "paiement")
    private Reservation reservations;

}


