package com.Hayati.Reservation.des.Hotels.repositoriy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hayati.Reservation.des.Hotels.entity.User;
import com.Hayati.Reservation.des.Hotels.entity.VerificationCode;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
@Modifying
@Query("DELETE FROM VerificationCode v WHERE v.email = :email")
void deleteByEmail(@Param("email") String email);
    Optional<VerificationCode> findByCode(String code);
    @Query("DELETE FROM VerificationCode vc WHERE vc.code = :code")
    void deleteByCode(String code);
}



