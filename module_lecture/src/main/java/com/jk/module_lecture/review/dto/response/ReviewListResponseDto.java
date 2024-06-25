package com.jk.module_lecture.review.dto.response;

import com.jk.module_lecture.review.entity.Review;
import lombok.Builder;

@Builder
public record ReviewListResponseDto(
        Long reviewId,
        Long lectureId,
        Long userId,
        String content,
        Integer star,
        Boolean status) {

    public static ReviewListResponseDto toDto(Review review) {
        return ReviewListResponseDto.builder()
                .reviewId(review.getReviewId())
                .lectureId(review.getLecture().getLectureId())
                .userId(review.getUserId())
                .content(review.getContent())
                .star(review.getStar())
                .status(review.getStatus())
                .build();
    }
}