package com.jk.module_lecture.lecture.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record LectureUpdateResponseDto(
        Long lectureId,
        String title,
        String description,
        Long teacherId,
        BigDecimal price,
        LocalDateTime updatedAt
) {
    public static LectureUpdateResponseDto toDto(Lecture lecture) {
        return LectureUpdateResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .teacherId(lecture.getTeacherId())
                .price(lecture.getPrice())
                .updatedAt(lecture.getUpdatedAt())
                .build();
    }
}