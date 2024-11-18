package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Hayati.Reservation.des.Hotels.entity.Notification;
import java.util.List;

import org.springframework.data.repository.query.Param;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.hotel.id_hot = :hotelId")
    List<Notification> findByHotelId(@Param("hotelId") Long hotelId);


    
    List<Notification> findByAdminId(Integer adminId);

}
