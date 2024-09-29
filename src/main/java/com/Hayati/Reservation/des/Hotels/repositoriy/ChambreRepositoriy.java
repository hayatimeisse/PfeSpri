package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.Hayati.Reservation.des.Hotels.entity.Chambre;
import com.Hayati.Reservation.des.Hotels.entity.Suite;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChambreRepositoriy extends JpaRepository<Chambre, Long> {

    @Query("SELECT c FROM Chambre c WHERE c.suite.hotel.id_hot = :hotelId")
    List<Chambre> findByHotelId(@Param("hotelId") Long hotelId);
}



