package com.jk.module_lecture.review.service.dto;

import com.jk.module_lecture.review.entity.Review;
import lombok.Builder;

@Builder
public record ReviewListDto(
        Long reviewId,
        Long lectureId,
        Long userId,
        String content,
        Integer star,
        Boolean status) {

    public static ReviewListDto toDto(Review review) {
        return ReviewListDto.builder()
                .reviewId(review.getReviewId())
                .lectureId(review.getLecture().getLectureId())
                .userId(review.getUserId())
                .content(review.getContent())
                .star(review.getStar())
                .status(review.getStatus())
                .build();
    }
}