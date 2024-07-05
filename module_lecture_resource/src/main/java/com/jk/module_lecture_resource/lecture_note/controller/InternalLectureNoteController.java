package com.jk.module_lecture_resource.lecture_note.controller;

import com.jk.module_lecture_resource.lecture_note.service.LectureNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/lectures/notes")
public class InternalLectureNoteController {
    private final LectureNoteService lectureNoteService;
}
