package com.Hayati.Reservation.des.Hotels.repositoriy;

import com.Hayati.Reservation.des.Hotels.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByVerificationCode(String verificationCode);

    boolean existsByEmail(String email);
}
