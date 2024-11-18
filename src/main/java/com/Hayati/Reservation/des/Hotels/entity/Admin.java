package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "admin")
public class Admin extends User {
   
   

    @Override
    public Admin setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public Admin setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Admin setPassword(String password) {
        super.setPassword(password);
        return this;
    }

  
}
