package com.jk.module_lecture.review.controller;

import com.jk.module_lecture.common.dto.ApiResponseDto;
import com.jk.module_lecture.review.dto.request.ReviewCreateRequestDto;
import com.jk.module_lecture.review.dto.request.ReviewUpdateRequestDto;
import com.jk.module_lecture.review.dto.response.ReviewListResponseDto;
import com.jk.module_lecture.review.dto.response.ReviewResponseDto;
import com.jk.module_lecture.review.service.ReviewService;
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
    public ResponseEntity<ApiResponseDto<ReviewResponseDto>> createReview(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        ReviewResponseDto responseDto = reviewService.createReview(
                reviewCreateRequestDto.lectureId(),
                reviewCreateRequestDto.userId(),
                reviewCreateRequestDto.content(),
                reviewCreateRequestDto.star()
        );
        return new ResponseEntity<>(new ApiResponseDto<>(HttpStatus.CREATED, "리뷰가 생성되었습니다.", responseDto), HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<Void>> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto) {
        reviewService.updateReview(
                reviewId,
                reviewUpdateRequestDto.userId(),
                reviewUpdateRequestDto.content(),
                reviewUpdateRequestDto.star()
        );
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "리뷰가 업데이트되었습니다.", null));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteReview(
            @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "리뷰가 삭제되었습니다.", null));
    }

    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<ApiResponseDto<List<ReviewListResponseDto>>> getReviewsByLectureId(@PathVariable Long lectureId) {
        List<ReviewListResponseDto> reviews = reviewService.getReviewsByLectureId(lectureId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "강의에 대한 리뷰 목록입니다.", reviews));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<ReviewListResponseDto>>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewListResponseDto> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "사용자가 작성한 리뷰 목록입니다.", reviews));
    }
}