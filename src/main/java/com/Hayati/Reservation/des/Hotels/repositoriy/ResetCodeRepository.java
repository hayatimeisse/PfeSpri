package com.Hayati.Reservation.des.Hotels.repositoriy;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.ResetCode;

import java.util.Optional;

public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    Optional<ResetCode> findByEmail(String email);
}

