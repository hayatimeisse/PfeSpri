package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Hayati.Reservation.des.Hotels.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNumerodetelephone(String numerodetelephone);
}
