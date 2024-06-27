package com.jk.module_lecture_resource.multimedia.controller;

import com.jk.module_lecture_resource.common.dto.ApiResponseDto;
import com.jk.module_lecture_resource.multimedia.dto.request.MultimediaUpdateRequestDto;
import com.jk.module_lecture_resource.multimedia.dto.response.MultimediaResponseDto;
import com.jk.module_lecture_resource.multimedia.service.MultimediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures/multimedia")
public class MultimediaController {
    private final MultimediaService multimediaService;

    /**
     * 멀티미디어 파일 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<MultimediaResponseDto>> create(
            @RequestParam("lectureId") Long lectureId,
            @RequestParam("file") MultipartFile file
    ) {
        MultimediaResponseDto responseDto = multimediaService.create(lectureId, file);

        return ResponseEntity
                .created(URI.create("api/v1/lectures/multimedia/" + responseDto.fileId()))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "멀티미디어 파일이 등록됐습니다.", responseDto));
    }

    /**
     * 멀티미디어 파일 수정
     */
    @PutMapping("/{fileId}")
    public ResponseEntity<ApiResponseDto<MultimediaResponseDto>> update(
            @PathVariable Long fileId,
            @RequestParam("file") MultipartFile file
    ) {
        MultimediaUpdateRequestDto request = MultimediaUpdateRequestDto.builder()
                .file(file)
                .build();
        MultimediaResponseDto responseDto = multimediaService.update(fileId, request);

        return ResponseEntity
                .ok()
                .body(new ApiResponseDto<>(HttpStatus.OK, "멀티미디어 파일이 업데이트됐습니다.", responseDto));
    }

    /**
     * 멀티미디어 파일 삭제
     */
    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long fileId) {
        multimediaService.delete(fileId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "멀티미디어 파일이 삭제됐습니다.", null));
    }
}