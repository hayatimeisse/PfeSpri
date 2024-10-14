package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Hayati.Reservation.des.Hotels.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailOrNumerodetelephone(String email, String numerodetelephone);
    Optional<User> findByEmail(String email);
    Optional<User> findByNumerodetelephone(String numerodetelephone);
    Optional<User> findById(Long email);

}
