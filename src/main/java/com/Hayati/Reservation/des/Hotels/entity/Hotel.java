package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hot")
    private Long id_hot;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String emplacement;

    @Column(nullable = false)
    private String evaluation;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String commentaires;

    @Column(nullable = false)
    private String notifications;
    @ManyToOne
    @JoinColumn(name = "subscribe_id", referencedColumnName = "id", nullable = true)
    private Subscribe subscribe;
    

    // @OneToMany(mappedBy = "hotel")
    // private Set<Employee> employees;

       @OneToMany(mappedBy = "hotel")
    @JsonManagedReference
    private Set<Suite> suites;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "hotel_services",
        joinColumns = @JoinColumn(name = "hotel_id", referencedColumnName = "id_hot"), // Corrigé ici
        inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id_ser")
    )
    
    private Set<ServicesDisponibles> servicesDisponibles;

    @Column(nullable = false)
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATTEND;
}
