package com.Hayati.Reservation.des.Hotels.repositoriy;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.Paiement;


public interface PaiementRepositoriy extends JpaRepository<Paiement,Long>{
    
}

