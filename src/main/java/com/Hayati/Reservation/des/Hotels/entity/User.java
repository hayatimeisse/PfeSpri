package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*; 
import lombok.*;
import java.util.Set;

@Data
@Entity

@Table(name = "User", uniqueConstraints = {
     //   @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    // @Column(nullable = false)
    // private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String numerodetelephone;
    // @Column(nullable = false)
    // private String adresse;

    @OneToMany(mappedBy = "users")
    private Set<Reservation> reservations;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
  
   
}
