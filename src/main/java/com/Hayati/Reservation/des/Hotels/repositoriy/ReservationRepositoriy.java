package com.Hayati.Reservation.des.Hotels.repositoriy;





import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.Reservation;



public interface ReservationRepositoriy extends JpaRepository<Reservation, Long> {
   
}

