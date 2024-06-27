package com.jk.module_lecture_resource.lecture_note.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LectureNoteCreateRequestDto(
        @NotNull Long lectureId,
        @NotBlank String title
) {
}