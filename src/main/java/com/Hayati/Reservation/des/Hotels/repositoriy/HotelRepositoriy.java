package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;

import java.util.List;

public interface HotelRepositoriy extends JpaRepository<Hotel, Long> {
    // @Query("SELECT s FROM Hotel s WHERE s.subscribe.id = :subscribeId")
    // List<Hotel> findBySubscribeId(@Param("subscribeId") Long subscribeId);
    List<Hotel> findBySubscribeId(Long subscribeId);
    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.subscribe")
    List<Hotel> findAllWithSubscribe();
}
