package com.zerobase.reservation.service;

import com.zerobase.reservation.ReservationApplication;
import com.zerobase.reservation.entity.StoreEntity;
import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.exception.impl.NoStoreException;
import com.zerobase.reservation.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplication.class);


    /**
     * 매장 추가
     * @param storeDto 매장명, 매장위치, 매장설명 dto
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StoreDto createStore(StoreDto storeDto) {
        logger.info("started to create store");

        this.storeRepository.save(StoreEntity.builder()
                                        .name(storeDto.getName())
                                        .location(storeDto.getLocation())
                                        .explanation(storeDto.getExplanation())
                                        .build());

        logger.info("end to create store");
        return storeDto;
    }

    /**
     * 매장 수정
     * @param storeDto 매장명, 매장위치, 매장설명 dto
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StoreDto modifyStore(StoreDto storeDto) {
        logger.info("started to modify store");

        String name = storeDto.getName();

        var store = this.storeRepository.findByName(name)
                                    .orElseThrow(() -> new NoStoreException());

        this.storeRepository.save(StoreEntity.builder()
                                        .id(store.getId())
                                        .name(name)
                                        .location(storeDto.getLocation())
                                        .explanation(storeDto.getExplanation())
                                        .build());

        logger.info("end to modify store");

        return storeDto;
    }

    /**
     * 매장 삭제
     * @param name 매장 이름
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String deleteStore(String name) {
        logger.info("started to delete store");

        var store = this.storeRepository.findByName(name)
                                    .orElseThrow(() -> new NoStoreException());
        this.storeRepository.deleteById(store.getId());

        logger.info("end to delete store");
        return name;
    }

    /**
     * 매장 목록 조회 (가나다 순)
     * @return
     */
    public List<StoreDto> selectStoreOrderByName() {
        logger.info("started to select store");

        List<StoreEntity> storeEntityList = this.storeRepository.findAllByOrderByName();

        List<StoreDto> storeDtoList = storeEntityList.stream().map(StoreDto::fromEntity).collect(Collectors.toList());

        logger.info("end to select store");

        return storeDtoList;

    }

    /**
     * 매장 상세 정보
     * @param name
     * @return
     */
    public StoreDto selectStoreDetail(String name) {
        logger.info("started to select store");

        var storeEntity = this.storeRepository.findByName(name)
                        .orElseThrow(() -> new NoStoreException());

        logger.info("end to select store");
        return StoreDto.fromEntity(storeEntity);
    }
}
