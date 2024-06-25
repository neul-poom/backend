package com.jk.module_lecture.lecture.controller;

import com.jk.module_lecture.common.dto.ApiResponseDto;
import com.jk.module_lecture.lecture.dto.request.LectureCreateRequestDto;
import com.jk.module_lecture.lecture.dto.request.LectureUpdateRequestDto;
import com.jk.module_lecture.lecture.dto.response.*;
import com.jk.module_lecture.lecture.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures")
public class LectureController {
    private final LectureService lectureService;

    /**
     * 강의 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<LectureCreateResponseDto>> create(
            @Valid @RequestBody LectureCreateRequestDto request
    ) {
        LectureCreateResponseDto responseDto = lectureService.create(request.title(), request.description(), request.teacherId(), request.price());

        return ResponseEntity
                .created(URI.create("api/v1/lectures/" + responseDto.lectureId()))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "강의가 등록됐습니다.", responseDto));
    }

    /**
     * 강의 수정
     */
    @PutMapping("/{lectureId}")
    public ResponseEntity<ApiResponseDto<LectureUpdateResponseDto>> update(
            @PathVariable Long lectureId,
            @Valid @RequestBody LectureUpdateRequestDto request
    ) {
        LectureUpdateResponseDto responseDto = lectureService.update(lectureId, request.title(), request.description(), request.teacherId(), request.price());

        return ResponseEntity
                .ok()
                .body(new ApiResponseDto<>(HttpStatus.OK, "강의가 업데이트됐습니다.", responseDto));
    }

    /**
     * 강의 상세정보 조회
     */
    @GetMapping("/{lectureId}")
    public ResponseEntity<ApiResponseDto<LectureDetailResponseDto>> getLectureInfoById(
            @PathVariable(name = "lectureId") Long lectureId
    ) {
        LectureDetailResponseDto response = lectureService.getLectureInfoById(lectureId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "강의 상세정보 조회", response));
    }

    /**
     * 전체 강의 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<LectureListResponseDto>>> getAllLectures() {
        List<LectureListResponseDto> lectures = lectureService.getAllLectures();
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "전체 강의 조회", lectures));
    }

    /**
     * 강의 삭제
     */
    @DeleteMapping("/{lectureId}")
    public ResponseEntity<ApiResponseDto<LectureDeleteResponseDto>> deleteLecture(@PathVariable Long lectureId) {
        LectureDeleteResponseDto responseDto = lectureService.delete(lectureId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "강의가 삭제됐습니다.", responseDto));
    }
}
