package com.zerobase.reservation.dto;

import com.zerobase.reservation.entity.ReservationEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private String name;

    private Date date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private Timestamp time;

    private String phone;

    private int yn;

    private int arrive;

    public static ReservationDto fromEntity(ReservationEntity reservationEntity) {
        return ReservationDto.builder()
                .name(reservationEntity.getName())
                .date(reservationEntity.getDate())
                .time(reservationEntity.getTime())
                .phone(reservationEntity.getPhone())
                .yn(reservationEntity.getYn())
                .arrive(reservationEntity.getArrive())
                .build();
    }
}
