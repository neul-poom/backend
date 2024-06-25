package com.jk.module_lecture.lecture.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LectureCreateRequestDto (
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        Long teacherId,
        @NotNull
        BigDecimal price
) {

}
