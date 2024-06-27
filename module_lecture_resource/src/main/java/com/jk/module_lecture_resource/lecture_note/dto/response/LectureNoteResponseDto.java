package com.jk.module_lecture_resource.lecture_note.dto.response;

import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import lombok.Builder;

@Builder
public record LectureNoteResponseDto(
        Long noteId,
        Long lectureId,
        String title,
        String fileUrl
) {
    public static LectureNoteResponseDto toDto(LectureNote lectureNote) {
        return LectureNoteResponseDto.builder()
                .noteId(lectureNote.getNoteId())
                .lectureId(lectureNote.getLectureId())
                .title(lectureNote.getTitle())
                .fileUrl(lectureNote.getFileUrl())
                .build();
    }
}