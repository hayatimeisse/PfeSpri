package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.Hayati.Reservation.des.Hotels.entity.Chambre;


public interface ChambreRepositoriy extends JpaRepository<Chambre, Long> {
     @Query("SELECT c FROM Chambre c WHERE c.suite.hotel.id_hot = :hotelId")
     List<Chambre> findByHotelId(Long hotelId);
     @Query("SELECT c FROM Chambre c WHERE c.suite.id = :suiteId AND c.suite.hotel.subscribe.id = :subscribeId")
     List<Chambre> findBySuiteIdAndSubscribeId(@Param("suiteId") Long suiteId, @Param("subscribeId") Long subscribeId);
     
 }