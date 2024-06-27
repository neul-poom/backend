package com.jk.module_lecture_resource.lecture_note.controller;

import com.jk.module_lecture_resource.common.dto.ApiResponseDto;
import com.jk.module_lecture_resource.lecture_note.dto.response.LectureNoteResponseDto;
import com.jk.module_lecture_resource.lecture_note.service.LectureNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures/notes")
public class LectureNoteController {
    private final LectureNoteService lectureNoteService;

    /**
     * 강의 노트 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<LectureNoteResponseDto>> create(
            @RequestParam("lectureId") Long lectureId,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) {
        LectureNoteResponseDto responseDto = lectureNoteService.create(lectureId, title, file);

        return ResponseEntity
                .created(URI.create("api/v1/lectures/notes/" + responseDto.noteId()))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "강의 노트가 등록됐습니다.", responseDto));
    }

    /**
     * 강의 노트 수정
     */
    @PutMapping("/{noteId}")
    public ResponseEntity<ApiResponseDto<LectureNoteResponseDto>> update(
            @PathVariable Long noteId,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file
    ) {
        LectureNoteResponseDto responseDto = lectureNoteService.update(noteId, title, file);

        return ResponseEntity
                .ok()
                .body(new ApiResponseDto<>(HttpStatus.OK, "강의 노트가 업데이트됐습니다.", responseDto));
    }

    /**
     * 강의 노트 삭제
     */
    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long noteId) {
        lectureNoteService.delete(noteId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "강의 노트가 삭제됐습니다.", null));
    }
}