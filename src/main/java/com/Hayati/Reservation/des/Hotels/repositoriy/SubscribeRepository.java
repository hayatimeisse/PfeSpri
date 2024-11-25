package com.Hayati.Reservation.des.Hotels.repositoriy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Hayati.Reservation.des.Hotels.entity.Subscribe;
import com.Hayati.Reservation.des.Hotels.entity.Suite;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Subscribe findByVerificationCode(String verificationCode);
}
