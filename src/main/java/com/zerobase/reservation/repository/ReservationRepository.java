package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.MemberEntity;
import com.zerobase.reservation.entity.ReservationEntity;
import com.zerobase.reservation.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByYnOrderByDate(int yn);

    Optional<ReservationEntity> findById(long id);
}
