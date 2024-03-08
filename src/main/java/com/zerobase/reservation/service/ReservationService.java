package com.zerobase.reservation.service;

import com.zerobase.reservation.ReservationApplication;
import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.entity.ReservationEntity;
import com.zerobase.reservation.entity.StoreEntity;
import com.zerobase.reservation.exception.impl.*;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplication.class);

    /**
     * 매장 예약 요청
     * @param reservationDto 매장명, 시간, 핸드폰번호, 승인/거절 dto
     * @return 매장 정보
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationEntity createReservation(ReservationDto reservationDto) {
        logger.info("started to create reservation");

        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        ReservationEntity reservationEntity = ReservationEntity.builder()
                                                                .name(reservationDto.getName())
                                                                .date(sqlDate)
                                                                .time(reservationDto.getTime())
                                                                .phone(reservationDto.getPhone())
                                                                .yn(0)
                                                                .arrive(0)
                                                                .build();

        this.reservationRepository.save(reservationEntity);

        logger.info("end to create reservation");
        return reservationEntity;
    }

    /**
     * 점주 예약 정보 확인
     * @return 테이블 목록
     */
    public List<ReservationEntity> selectReservationListByDate() {
        logger.info("started to select reservation");

        List<ReservationEntity> reservationEntityList = this.reservationRepository.findAllByYnOrderByDate(0);

        logger.info("end to select reservation");
        return reservationEntityList;
    }

    /**
     * 승인 예약/거절 처리
     * @param id 예약 번호
     * @param yn 승인 예약/거절
     * @return 처리한 dto
     */
    public ReservationDto modifyReservation(long id, int yn) {
        logger.info("started to modify reservation");

        var reservation = this.reservationRepository.findById(id)
                .orElseThrow(() -> new NoReservationException());

        this.reservationRepository.save(ReservationEntity.builder()
                                    .id(id)
                                    .name(reservation.getName())
                                    .date(reservation.getDate())
                                    .time(reservation.getTime())
                                    .phone(reservation.getPhone())
                                    .yn(yn)
                                    .arrive(reservation.getArrive())
                                    .build());

        logger.info("end to modify reservation");

        return ReservationDto.fromEntity(reservation);
    }

    /**
     * 방문확인
     * @param reservationEntity 예약 entity
     * @return reservationDto
     */
    public ReservationDto arriveReservation(ReservationEntity reservationEntity) {

        var reservation = this.reservationRepository.findById(reservationEntity.getId())
                .orElseThrow(() -> new NoReservationException());

        // 유효성 검사
        if(!reservationEntity.getName().equals(reservation.getName())) {
            throw new NotEqualNameException();
        }

        if(!reservationEntity.getPhone().equals(reservation.getPhone())) {
            throw new NotEqualPhoneException();
        }

        if(reservation.getYn() != 2) {
            throw new NotAllowReservationException();
        }

        // 10분 전에 와야 도착 확인 가능
        Timestamp timestamp1 = reservation.getTime();
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();

        cal.setTime(timestamp1);
        cal.add(Calendar.HOUR, -9);
        cal.add(Calendar.MINUTE, -10);
        timestamp1.setTime(cal.getTime().getTime());

        if(timestamp1.before(timestamp2)) {
            throw new ArriveLateException();
        }


        ReservationEntity entity = ReservationEntity.builder()
                                                    .id(reservationEntity.getId())
                                                    .name(reservation.getName())
                                                    .date(reservation.getDate())
                                                    .time(reservation.getTime())
                                                    .phone(reservation.getPhone())
                                                    .yn(reservation.getYn())
                                                    .arrive(1)
                                                    .build();

        this.reservationRepository.save(entity);
        return ReservationDto.fromEntity(entity);
    }
}
