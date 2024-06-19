package com.jk.module_lecture.lecture.controller;

import com.jk.module_lecture.lecture.controller.dto.request.LectureCreateRequestDto;
import com.jk.module_lecture.lecture.controller.dto.request.LectureUpdateRequestDto;
import com.jk.module_lecture.lecture.controller.dto.response.LectureCreateResponseDto;
import com.jk.module_lecture.lecture.service.LectureService;
import com.jk.module_lecture.lecture.service.dto.response.LectureDetailResponseDto;
import com.jk.module_lecture.lecture.service.dto.response.LectureListResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<Void> create(
            @Valid @RequestBody LectureCreateRequestDto request
    ) {
        Long lectureId =
                lectureService.create(request.title(), request.description(), request.teacherId(),
                        request.price());

        return ResponseEntity.created(URI.create("api/v1/lectures/" + lectureId)).build();
    }

    /**
     * 강의 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(name = "id") Long lectureId,
            @Valid @RequestBody LectureUpdateRequestDto request
            ) {
        lectureService.update(lectureId, request.title(), request.description(), request.teacherId(),
                request.price());
        return ResponseEntity.ok().build();
    }

    /**
     * 강의 상세정보 조회
     */
    @GetMapping("/{lectureId}")
    public ResponseEntity<LectureCreateResponseDto> getLectureInfoById(
            @PathVariable(name = "lectureId") Long lectureId
    ) {
        LectureCreateResponseDto response =
                LectureCreateResponseDto.dtoToResponseDto(lectureService.getLectureInfoById(lectureId));
        return ResponseEntity.ok().body(response);
    }

    /**
     * 전체 강의 조회
     */
    @GetMapping
    public ResponseEntity<List<LectureListResponseDto>> getAllLectures() {
        List<LectureListResponseDto> lectures = lectureService.getAllLectures();
        return ResponseEntity.ok(lectures);
    }

    /**
     * 강의 삭제
     */
    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long lectureId) {
        lectureService.delete(lectureId);
        return ResponseEntity.noContent().build();
    }
}
