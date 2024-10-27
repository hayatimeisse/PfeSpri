package com.Hayati.Reservation.des.Hotels.entity;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client extends User {

    @Column(nullable = false)
    private String photo;
    @Column(nullable = false)
    private boolean isEmailVerified = false;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    public Client setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
        return this;
    }
    @Override
    public Client setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public Client setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Client setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public Client setNumerodetelephone(String numerodetelephone) {
        super.setNumerodetelephone(numerodetelephone);
        return this;
    }

    public Client setPhoto(String photo) {
        this.photo = photo;
        return this;
    }
}

