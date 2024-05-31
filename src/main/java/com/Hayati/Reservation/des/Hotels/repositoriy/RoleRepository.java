package com.Hayati.Reservation.des.Hotels.repositoriy;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hayati.Reservation.des.Hotels.entity.Role;





public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}