package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.ReservationDto;
import com.zerobase.reservation.dto.ReviewDto;
import com.zerobase.reservation.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    /**
     * 리뷰 작성
     * @param reviewDto 작성자 이름, 작성자 핸드폰, 점수, 내용, 예약번호 dto
     * @return 작성한 리뷰
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto review = this.reviewService.createReview(reviewDto);
        return ResponseEntity.ok(review);
    }

    /**
     * 리뷰 수정
     * @param reviewDto 작성자 이름, 작성자 핸드폰, 점수, 내용, 예약번호 dto
     * @return 수정한 리뷰
     */
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> modifyReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto review = this.reviewService.modifyReview(reviewDto);
        return ResponseEntity.ok(review);
    }

    /**
     * 리뷰 삭제
     * @param reservation_id 예약번호
     * @return 삭제한 리뷰
     */
    @DeleteMapping("{reservation_id}")
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteReivew(@PathVariable(name = "reservation_id") Long reservation_id) {
        ReviewDto review = this.reviewService.deleteReview(reservation_id);
        return ResponseEntity.ok(review);
    }

}
