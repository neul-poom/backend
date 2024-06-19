package com.jk.module_lecture.like.controller;

import com.jk.module_lecture.like.controller.dto.request.LikeRequestDto;
import com.jk.module_lecture.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lectures/{lectureId}/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> toggleLike(@PathVariable(name = "lectureId") Long lectureId,
                                           @RequestBody LikeRequestDto request) {
        likeService.toggleLike(request.userId(), lectureId);
        return ResponseEntity.ok().build();
    }
}
