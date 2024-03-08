package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.ReservationEntity;
import com.zerobase.reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 매장 예약 요청
     * @param reservationDto 매장명, 날짜, 시간, 핸드폰번호, 승인/거절, 방문확인 dto
     * @return 매장 정보
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationEntity reservationEntity = this.reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(reservationEntity);
    }

    /**
     * 점주 예약 정보 확인
     * @return 조회할 리스트
     */
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> selectReservationListByDate() {
        List<ReservationEntity> list = this.reservationService.selectReservationListByDate();
        return ResponseEntity.ok(list);
    }

    /**
     * 승인 예약/거절 처리
     * @param id 예약 번호
     * @param yn 승인 예약/거절
     * @return 처리한 dto
     */
    @PutMapping("/{id}/{yn}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> modifyReservation(@PathVariable(name = "id")long id, @PathVariable(name = "yn")int yn) {
        ReservationDto reservationDto = this.reservationService.modifyReservation(id, yn);
        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 방문확인
     * @param reservationEntity 예약 entity
     * @return reservationDto
     */
    @PutMapping("/arrive")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> arriveReservation(@RequestBody ReservationEntity reservationEntity) {
        ReservationDto reservationDto = this.reservationService.arriveReservation(reservationEntity);
        return ResponseEntity.ok(reservationDto);
    }
}
