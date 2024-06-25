package com.jk.module_lecture.like.service;

import com.jk.module_lecture.common.exception.CustomException;
import com.jk.module_lecture.common.exception.ErrorCode;
import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import com.jk.module_lecture.like.dto.response.LikeResponseDto;
import com.jk.module_lecture.like.entity.Like;
import com.jk.module_lecture.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LectureRepository lectureRepository;

    /**
     * 좋아요 요청(토글 방식)
     */
    @Transactional
    public LikeResponseDto toggleLike(Long userId, Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        boolean isLiked = likeRepository.findByUserIdAndLecture_LectureId(userId, lectureId)
                .map(like -> {
                    likeRepository.delete(like);
                    return false;
                })
                .orElseGet(() -> {
                    likeRepository.save(Like.builder()
                            .userId(userId)
                            .lecture(lecture)
                            .build());
                    return true;
                });

        return new LikeResponseDto(userId, lectureId, isLiked);
    }
}