package com.zerobase.reservation.service;

import com.zerobase.reservation.ReservationApplication;
import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.dto.ReviewDto;
import com.zerobase.reservation.entity.ReviewEntity;
import com.zerobase.reservation.exception.impl.*;
import com.zerobase.reservation.repository.ReservationRepository;
import com.zerobase.reservation.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReservationRepository reservationRepository;

    private final ReviewRepository reviewRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservationApplication.class);

    /**
     * 리뷰 작성
     * @param reviewDto 작성자 이름, 작성자 핸드폰, 점수, 내용, 예약번호 dto
     * @return 작성한 리뷰
     */
    public ReviewDto createReview(ReviewDto reviewDto) {

        var reservation = this.reservationRepository.findById(reviewDto.getReservation_id())
                .orElseThrow(() -> new NoReservationException());

        // 예약자인지 확인
        if(!reviewDto.getName().equals(reservation.getName())) {
            throw new NotEqualNameException();
        }

        if(!reviewDto.getPhone().equals(reservation.getPhone())) {
            throw new NotEqualPhoneException();
        }

        if(reservation.getArrive() != 1) {
            throw new NotArriveReservationException();
        }

        ReviewEntity reviewEntity = ReviewEntity.builder()
                                                .name(reviewDto.getName())
                                                .phone(reviewDto.getPhone())
                                                .score(reviewDto.getScore())
                                                .text(reviewDto.getText())
                                                .reservationId(reviewDto.getReservation_id())
                                                .member_name(reviewDto.getMember_name())
                                                .build();

        reviewRepository.save(reviewEntity);

        return reviewDto;
    }

    /**
     * 리뷰 수정
     * @param reviewDto 작성자 이름, 작성자 핸드폰, 점수, 내용, 예약번호 dto
     * @return 수정한 리뷰
     */
    public ReviewDto modifyReview(ReviewDto reviewDto) {
        var review = this.reviewRepository.findByReservationId(reviewDto.getReservation_id())
                .orElseThrow(() -> new NoReviewException());

        // 예약자인지 확인
        if(!reviewDto.getMember_name().equals(review.getMember_name())) {
            throw new NotEqualMemberNameException();
        }

        ReviewEntity reviewEntity = ReviewEntity.builder()
                                                .id(review.getId())
                                                .name(review.getName())
                                                .phone(review.getPhone())
                                                .score(reviewDto.getScore())
                                                .text(reviewDto.getText())
                                                .reservationId(reviewDto.getReservation_id())
                                                .member_name(reviewDto.getMember_name())
                                                .build();

        reviewRepository.save(reviewEntity);

        reviewDto.setName(review.getName());
        reviewDto.setPhone(review.getPhone());

        return reviewDto;
    }

    /**
     * 리뷰 삭제
     * @param reservation_id 예약 번호
     * @return 삭제한 리뷰
     */
    public ReviewDto deleteReview(Long reservation_id) {

        var review = this.reviewRepository.findByReservationId(reservation_id)
                .orElseThrow(() -> new NoReviewException());

        reviewRepository.deleteById(review.getId());

        return ReviewDto.fromEntity(review);
    }
}
