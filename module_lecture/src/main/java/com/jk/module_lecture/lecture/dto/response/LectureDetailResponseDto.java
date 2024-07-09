package com.jk.module_lecture.lecture.dto.response;

import com.jk.module_lecture.lecture.entity.Lecture;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record LectureDetailResponseDto(
        Long lectureId,
        String title,
        String description,
        Long teacherId,
        BigDecimal price,
        String fileUrl,
        String videoUrl,
        LocalDateTime createdAt
) {
    public static LectureDetailResponseDto toDto(Lecture lecture, String fileUrl, String videoUrl) {
        return LectureDetailResponseDto.builder()
                .lectureId(lecture.getLectureId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .teacherId(lecture.getTeacherId())
                .price(lecture.getPrice())
                .fileUrl(fileUrl)
                .videoUrl(videoUrl)
                .createdAt(lecture.getCreatedAt())
                .build();
    }
}