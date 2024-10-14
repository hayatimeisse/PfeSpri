package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hayati.Reservation.des.Hotels.entity.Client;



@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Custom queries can go here if needed
}
