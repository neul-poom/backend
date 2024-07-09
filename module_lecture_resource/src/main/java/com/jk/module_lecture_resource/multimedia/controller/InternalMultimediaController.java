package com.jk.module_lecture_resource.multimedia.controller;

import com.jk.module_lecture_resource.multimedia.service.MultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/internal/lectures/multimedia")
public class InternalMultimediaController {
    public final MultimediaService multimediaService;

    /**
     * 강의 노트 생성
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createVideo(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "lectureId") Long lectureId
    ) {
        multimediaService.create(lectureId, file);
        return ResponseEntity.ok().build();
    }

    /**
     * 강의 노트 조회
     */
    @GetMapping("{lectureId}")
    public ResponseEntity<String> getNote(
            @PathVariable(name = "lectureId") Long lectureId
    ) {
        String response = multimediaService.getVideoDetails(lectureId);

        return ResponseEntity.ok().body(response);
    }

}
