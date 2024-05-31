package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.Hotel;



public interface HotelRepositoriy extends JpaRepository<Hotel, Long> {
   
}
