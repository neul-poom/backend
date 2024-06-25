package com.jk.module_lecture.lecture.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LectureCreateResponseDto(
        Long lectureId,
        String title,
        String description,
        Long teacherId,
        BigDecimal price
) {
    public static LectureCreateResponseDto dtoToResponseDto(LectureDetailResponseDto lectureDetailResponseDto) {
        return LectureCreateResponseDto.builder()
                .lectureId(lectureDetailResponseDto.lectureId())
                .title(lectureDetailResponseDto.title())
                .description(lectureDetailResponseDto.description())
                .teacherId(lectureDetailResponseDto.teacherId())
                .price(lectureDetailResponseDto.price())
                .build();
    }
}