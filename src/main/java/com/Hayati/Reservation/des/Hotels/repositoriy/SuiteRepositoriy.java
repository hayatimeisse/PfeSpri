package com.Hayati.Reservation.des.Hotels.repositoriy;

import com.Hayati.Reservation.des.Hotels.entity.Suite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuiteRepositoriy extends JpaRepository<Suite, Long> {

    @Query("SELECT s FROM Suite s WHERE s.hotel.id = :hotelId")
    List<Suite> findByHotelId(@Param("hotelId") Long hotelId);
}
