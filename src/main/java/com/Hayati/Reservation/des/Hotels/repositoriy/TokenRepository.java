package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Hayati.Reservation.des.Hotels.entity.Token;

import jakarta.transaction.Transactional;


public interface TokenRepository extends JpaRepository<Token, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.invalid = true WHERE t.user.id = :userId")
    void invalidateByUserId(Long userId);
}

