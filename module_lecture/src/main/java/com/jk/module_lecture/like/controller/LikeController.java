package com.jk.module_lecture.like.controller;

import com.jk.module_lecture.like.dto.request.LikeRequestDto;
import com.jk.module_lecture.like.dto.response.LikeResponseDto;
import com.jk.module_lecture.like.service.LikeService;
import com.jk.module_lecture.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures/{lectureId}/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<LikeResponseDto>> toggleLike(@PathVariable(name = "lectureId") Long lectureId,
                                                                   @RequestBody LikeRequestDto request) {
        LikeResponseDto responseDto = likeService.toggleLike(request.userId(), lectureId);
        if (responseDto.liked()) {
            return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "좋아요 성공", responseDto));
        } else {
            return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "좋아요 취소 성공", responseDto));
        }

    }
}