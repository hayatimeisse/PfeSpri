package com.Hayati.Reservation.des.Hotels.entity;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscribe")
public class Subscribe extends User {

    @Column(length = 500)
        private Status status;

    @Override
    public String getUsername() {
        return this.getEmail(); // Utilisez l'email comme nom d'utilisateur
    }
    

   

 
}
