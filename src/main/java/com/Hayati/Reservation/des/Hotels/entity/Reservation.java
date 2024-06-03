package com.Hayati.Reservation.des.Hotels.entity;

import java.util.Date;
import java.util.Set;

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
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="reservation_chambre",
     joinColumns = @JoinColumn(name="reservation_id", referencedColumnName="id_res"),
     inverseJoinColumns=@JoinColumn(name="chambre_id", referencedColumnName = "id_cham")
    )
    private Set<Chambre> chambres;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="reservation_hotel",
     joinColumns = @JoinColumn(name="reservation_id", referencedColumnName="id_res"),
     inverseJoinColumns=@JoinColumn(name="hotel_id", referencedColumnName = "id_hot")
    )
    private Set<Hotel> hotels;
}
