package com.jk.module_lecture.lecture.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LectureListResponseDto(
        Long lectureId,
        String title,
        String description,
        BigDecimal price
) {
    public static LectureListResponseDto toDto(Lecture lecture) {
        return LectureListResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .price(lecture.getPrice())
                .build();
    }
}