package com.Hayati.Reservation.des.Hotels.entity;





import jakarta.persistence.*; 
import lombok.*;

@Data
@Entity
@Table(name = "Utilisateur", uniqueConstraints = {
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
  
   
}
