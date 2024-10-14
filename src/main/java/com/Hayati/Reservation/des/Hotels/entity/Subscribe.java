package com.Hayati.Reservation.des.Hotels.entity;
import jakarta.persistence.*;
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
    public Subscribe setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Subscribe setPassword(String password) {
        super.setPassword(password);
        return this;
    }
}
