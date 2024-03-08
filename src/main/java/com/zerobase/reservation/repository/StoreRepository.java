package com.zerobase.reservation.repository;

import com.zerobase.reservation.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    Optional<StoreEntity> findByName(String name);

    List<StoreEntity> findAllByOrderByNameDesc();

    List<StoreEntity> findAllByOrderByName();
}
