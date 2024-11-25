package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Suite")
public class Suite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sui")  // Ensure column name matches with the database
    private Long id_sui;

    @Column(nullable = false)
    private float prixJour;

    @Column(nullable = false)
    private boolean disponibilites;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id_hot", nullable = false) // hotel_id matches in DB
    private Hotel hotel;

    // @OneToMany(mappedBy = "suite")
    // private Set<Chambre> chambres;
  @OneToMany(mappedBy = "suite")
    @JsonIgnore // Prevent serialization of the suites collection
    private Set<Chambre> chambres;
   
}