package com.jk.module_lecture.lecture.service.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;

@Builder
public record LectureListResponseDto(
    String title,
    String description,
    Long teacherId) {

        public static LectureListResponseDto toDto(Lecture lecture) {
            return LectureListResponseDto.builder()
                    .title(lecture.getTitle())
                    .description(lecture.getDescription())
                    .teacherId(lecture.getTeacherId())
                    .build();
        }
}
