package com.Hayati.Reservation.des.Hotels.repositoriy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Hayati.Reservation.des.Hotels.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNumerodetelephone(String numerodetelephone);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByNumerodetelephone(String numerodetelephone);
    Optional<User> findByEmailOrNumerodetelephone(String emailOrNumerodetelephone, String numerodetelephone);
}
