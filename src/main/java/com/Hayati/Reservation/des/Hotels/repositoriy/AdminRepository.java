package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.User;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
@Query("SELECT a FROM Admin a JOIN a.roles r WHERE r = :role")
Optional<Admin> findByRole(@Param("role") String role);

}
