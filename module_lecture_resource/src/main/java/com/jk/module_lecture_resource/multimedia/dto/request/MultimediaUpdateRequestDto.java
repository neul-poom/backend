package com.jk.module_lecture_resource.multimedia.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record MultimediaUpdateRequestDto(
        MultipartFile file
) {
}