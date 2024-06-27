package com.jk.module_lecture_resource.multimedia.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record MultimediaCreateRequestDto(
        @NotNull Long lectureId,
        @NotNull MultipartFile file
) {
}