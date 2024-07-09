package com.jk.module_lecture_resource.lecture_note.controller;

import com.jk.module_lecture_resource.lecture_note.dto.response.LectureNoteResponseDto;
import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import com.jk.module_lecture_resource.lecture_note.service.LectureNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/lectures/notes")
public class InternalLectureNoteController {
    private final LectureNoteService lectureNoteService;

    /**
     * 강의 노트 생성
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createNote(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "lectureId") Long lectureId,
            @RequestParam(name = "title") String title
    ) {
        lectureNoteService.create(lectureId, title, file);
        return ResponseEntity.ok().build();
    }

    /**
     * 강의 노트 조회
     */
    @GetMapping("{lectureId}")
    public ResponseEntity<String> getNote(
            @PathVariable(name = "lectureId") Long lectureId
    ) {
        String response = lectureNoteService.getNoteDetails(lectureId);

        return ResponseEntity.ok().body(response);
    }

}
