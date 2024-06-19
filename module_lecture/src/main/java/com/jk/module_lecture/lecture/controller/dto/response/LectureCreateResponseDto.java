package com.jk.module_lecture.lecture.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jk.module_lecture.lecture.service.dto.response.LectureDetailResponseDto;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LectureCreateResponseDto(
        String title,
        String description,
        Long teacherId,
        BigDecimal price
) {
    public static LectureCreateResponseDto dtoToResponseDto(LectureDetailResponseDto lectureDetailResponseDto) {
        return LectureCreateResponseDto.builder()
                .title(lectureDetailResponseDto.title())
                .description(lectureDetailResponseDto.description())
                .teacherId(lectureDetailResponseDto.teacherId())
                .price(lectureDetailResponseDto.price())
                .build();
    }
}
