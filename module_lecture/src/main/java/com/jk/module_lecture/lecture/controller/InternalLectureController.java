package com.jk.module_lecture.lecture.controller;

import com.jk.module_lecture.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/lectures")
public class InternalLectureController {
    private final LectureService lectureService;

}
