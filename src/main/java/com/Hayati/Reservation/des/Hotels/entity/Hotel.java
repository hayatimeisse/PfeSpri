package com.Hayati.Reservation.des.Hotels.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
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

    
   

    @OneToMany(mappedBy = "hotel")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "hotel")
    private Set<Suite> suites;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "hotel_services",
        joinColumns = @JoinColumn(name = "hotel_id", referencedColumnName = "id_hot"),
        inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id_ser")
    )
    private Set<ServicesDisponibles> servicesDisponibles;

    @Column(nullable = false)
    private String imageUrl; 

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
