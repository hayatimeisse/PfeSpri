package com.Hayati.Reservation.des.Hotels.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hayati.Reservation.des.Hotels.entity.CardInfo;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
}
