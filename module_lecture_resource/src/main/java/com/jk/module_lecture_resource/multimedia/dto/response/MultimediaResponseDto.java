package com.jk.module_lecture_resource.multimedia.dto.response;

import lombok.Builder;

@Builder
public record MultimediaResponseDto(
        Long fileId,
        Long lectureId,
        String fileUrl
) {
}