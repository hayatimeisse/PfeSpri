package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "admin")
public class Admin extends User {

    @Override
    public Admin setName(String name) {
        super.setName(name);  // Ensure you're using "name" and not "nom" if you're keeping it consistent with User
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
