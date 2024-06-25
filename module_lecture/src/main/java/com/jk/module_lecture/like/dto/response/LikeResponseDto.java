package com.jk.module_lecture.like.dto.response;

import lombok.Builder;

@Builder
public record LikeResponseDto(
        Long userId,
        Long lectureId,
        boolean liked
) {
}