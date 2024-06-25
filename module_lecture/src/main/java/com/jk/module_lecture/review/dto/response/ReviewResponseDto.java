package com.jk.module_lecture.review.dto.response;

import com.jk.module_lecture.review.entity.Review;
import lombok.Builder;

@Builder
public record ReviewResponseDto(
        Long reviewId,
        Long lectureId,
        Long userId,
        String content,
        Integer star,
        Boolean status) {

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .reviewId(review.getReviewId())
                .lectureId(review.getLecture().getLectureId())
                .userId(review.getUserId())
                .content(review.getContent())
                .star(review.getStar())
                .status(review.getStatus())
                .build();
    }
}