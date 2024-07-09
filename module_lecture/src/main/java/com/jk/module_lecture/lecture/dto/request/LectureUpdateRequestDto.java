package com.jk.module_lecture.lecture.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Builder
public record LectureUpdateRequestDto(
    String title,
    String description,
    BigDecimal price,
    MultipartFile file,
    MultipartFile video
) {

}
