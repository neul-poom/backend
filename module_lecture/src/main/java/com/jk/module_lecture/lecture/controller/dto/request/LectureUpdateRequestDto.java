package com.jk.module_lecture.lecture.controller.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LectureUpdateRequestDto(
    String title,
    String description,
    Long teacherId,
    BigDecimal price
) {

}
