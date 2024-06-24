package com.jk.module_lecture.review.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewUpdateRequestDto(
        @NotNull Long userId,
        @NotBlank String content,
        @NotNull Integer star
) {
}