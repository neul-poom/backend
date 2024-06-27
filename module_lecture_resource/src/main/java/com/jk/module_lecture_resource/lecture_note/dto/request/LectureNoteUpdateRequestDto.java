package com.jk.module_lecture_resource.lecture_note.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import lombok.Builder;

@Builder
public record LectureNoteUpdateRequestDto(
        @NotBlank String title,
        MultipartFile file
) {
}