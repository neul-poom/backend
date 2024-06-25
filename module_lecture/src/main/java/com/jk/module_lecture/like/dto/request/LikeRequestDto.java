package com.jk.module_lecture.like.dto.request;

import lombok.Builder;

@Builder
public record LikeRequestDto(
        Long userId
) {
}
