package com.jk.module_lecture.review.controller;

import com.jk.module_lecture.review.controller.dto.request.ReviewCreateRequestDto;
import com.jk.module_lecture.review.controller.dto.request.ReviewUpdateRequestDto;
import com.jk.module_lecture.review.service.ReviewService;
import com.jk.module_lecture.review.service.dto.ReviewListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Long> createReview(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        Long reviewId = reviewService.createReview(
                reviewCreateRequestDto.lectureId(),
                reviewCreateRequestDto.userId(),
                reviewCreateRequestDto.content(),
                reviewCreateRequestDto.star()
        );
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto) {
        reviewService.updateReview(
                reviewId,
                reviewUpdateRequestDto.userId(),
                reviewUpdateRequestDto.content(),
                reviewUpdateRequestDto.star()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<List<ReviewListDto>> getReviewsByLectureId(@PathVariable Long lectureId) {
        List<ReviewListDto> reviews = reviewService.getReviewsByLectureId(lectureId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewListDto>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewListDto> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }
}