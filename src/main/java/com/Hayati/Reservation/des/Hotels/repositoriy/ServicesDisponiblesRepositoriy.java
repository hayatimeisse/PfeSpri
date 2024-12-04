package com.Hayati.Reservation.des.Hotels.repositoriy;

import com.Hayati.Reservation.des.Hotels.entity.ServicesDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicesDisponiblesRepositoriy extends JpaRepository<ServicesDisponibles, Long> {

    // Query to get services by hotel ID
    @Query("SELECT s FROM Hotel h JOIN h.servicesDisponibles s WHERE h.id_hot = :hotelId")
    List<ServicesDisponibles> findServicesByHotelId(Long hotelId);
    @Query("SELECT s FROM ServicesDisponibles s WHERE s.id_ser IN :serviceIds")
    List<ServicesDisponibles> findAllByIds(List<Long> serviceIds);
}
