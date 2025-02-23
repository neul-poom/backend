package com.jk.module_lecture.review.service;

import com.jk.module_lecture.common.exception.CustomException;
import com.jk.module_lecture.common.exception.ErrorCode;
import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import com.jk.module_lecture.review.dto.response.ReviewListResponseDto;
import com.jk.module_lecture.review.dto.response.ReviewResponseDto;
import com.jk.module_lecture.review.entity.Review;
import com.jk.module_lecture.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final LectureRepository lectureRepository;

    /**
     * 리뷰 생성
     */
    @Transactional
    public ReviewResponseDto createReview(Long lectureId, Long userId, String content, Integer star) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        Review review = Review.builder()
                .lecture(lecture)
                .userId(userId)
                .content(content)
                .star(star)
                .build();

        Review savedReview = reviewRepository.save(review);
        return new ReviewResponseDto(savedReview.getReviewId(), lectureId, userId, content, star, savedReview.getStatus());
    }

    /**
     * 리뷰 수정
     */
    @Transactional
    public void updateReview(Long reviewId, Long userId, String content, Integer star) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }

        review.update(content, star);
    }

    /**
     * 리뷰 삭제 (상태 비활성화)
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        review.deactivate();
    }

    /**
     * 특정 강의에 대한 모든 리뷰 조회 (페이징)
     */
    public Page<ReviewListResponseDto> getReviewsByLectureId(Long lectureId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByLecture_LectureId(lectureId, pageable);
        return reviews.map(ReviewListResponseDto::toDto);
    }

    /**
     * 특정 사용자가 작성한 모든 리뷰 조회 (페이징)
     */
    public Page<ReviewListResponseDto> getReviewsByUserId(Long userId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUserId(userId, pageable);
        return reviews.map(ReviewListResponseDto::toDto);
    }
}