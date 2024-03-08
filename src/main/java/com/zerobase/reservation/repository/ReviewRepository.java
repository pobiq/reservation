package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.ReservationEntity;
import com.zerobase.reservation.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReservationId(long reservation_id);
}
