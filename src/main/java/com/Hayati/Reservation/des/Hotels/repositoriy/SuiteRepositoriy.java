package com.Hayati.Reservation.des.Hotels.repositoriy;

import com.Hayati.Reservation.des.Hotels.entity.Suite;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuiteRepositoriy extends JpaRepository<Suite, Long> {
    // @EntityGraph(attributePaths = {"chambres"})
    @Query("SELECT s FROM Suite s WHERE s.hotel.id_hot = :hotelId AND s.hotel.subscribe.id = :subscribeId")
    List<Suite> findByHotelIdAndSubscribeId(@Param("hotelId") Long hotelId, @Param("subscribeId") Long subscribeId);
    // List<Suite> findByHotel_IdHot(Long hotelId);
    
}
