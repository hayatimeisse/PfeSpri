package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private boolean read = false;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id_hot")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    public Notification(String message, Admin admin, Hotel hotel) {
        this.message = message;
        this.admin = admin;
        this.hotel = hotel;
    }
}
