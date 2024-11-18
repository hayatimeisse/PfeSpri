package com.Hayati.Reservation.des.Hotels.entity;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "subscribe")
public class Subscribe extends User {

    @Column(nullable = false)
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATTEND;

    // Fluent setters for chaining
    public Subscribe setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public Subscribe setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    @Email(message = "Email must be valid")
    public Subscribe setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Subscribe setPassword(String password) {
        super.setPassword(password);
        return this;
    }

    public Subscribe setStatus(Status status) {
        this.status = status;
        return this;
    }
}
