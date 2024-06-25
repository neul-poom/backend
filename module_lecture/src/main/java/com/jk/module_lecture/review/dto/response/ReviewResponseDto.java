package com.jk.module_lecture.review.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponseDto(
        Long reviewId,
        Long lectureId,
        Long userId,
        String content,
        Integer star,
        Boolean status) {
}