package com.jk.module_lecture.lecture.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record LectureDetailResponseDto(
        Long lectureId,
        String title,
        String description,
        Long teacherId,
        BigDecimal price,
        LocalDateTime createdAt
) {
    public static LectureDetailResponseDto toDto(Lecture lecture) {
        return LectureDetailResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .teacherId(lecture.getTeacherId())
                .price(lecture.getPrice())
                .createdAt(lecture.getCreatedAt())
                .build();
    }
}