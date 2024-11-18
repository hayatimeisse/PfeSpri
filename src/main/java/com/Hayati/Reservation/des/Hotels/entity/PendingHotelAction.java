// package com.Hayati.Reservation.des.Hotels.entity;


// import jakarta.persistence.*;
// import lombok.*;

// @Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Table(name = "pending_hotel_action")
// public class PendingHotelAction {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "hotel_id", nullable = false)
//     private Hotel hotel;

//     @Column(name = "action_type", nullable = false)
//     private String actionType; // "CREATE", "UPDATE", "DELETE"

//     @Column(name = "status", nullable = false)
//     private String status; // "PENDING", "APPROVED", "REJECTED"

//     @Column(name = "admin_comment")
//     private String adminComment;
// }

