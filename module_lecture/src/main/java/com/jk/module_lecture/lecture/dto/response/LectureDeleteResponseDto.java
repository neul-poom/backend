package com.jk.module_lecture.lecture.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LectureDeleteResponseDto(
        Long lectureId,
        LocalDateTime updatedAt,
        Boolean status
) {
    public static LectureDeleteResponseDto toDto(Lecture lecture) {
        return LectureDeleteResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .updatedAt(lecture.getUpdatedAt())
                .status(lecture.getStatus())
                .build();
    }
}