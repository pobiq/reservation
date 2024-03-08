package com.zerobase.reservation.dto;

import com.zerobase.reservation.entity.ReservationEntity;
import com.zerobase.reservation.entity.ReviewEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private String name;

    private String phone;

    private int score;

    private String text;

    private Long reservation_id;

    private String member_name;

    public static ReviewDto fromEntity(ReviewEntity reviewEntity) {
        return ReviewDto.builder()
                .name(reviewEntity.getName())
                .phone(reviewEntity.getPhone())
                .score(reviewEntity.getScore())
                .text(reviewEntity.getText())
                .reservation_id(reviewEntity.getReservationId())
                .member_name(reviewEntity.getMember_name())
                .build();
    }
}
