package com.jk.module_lecture.review.service;

import com.jk.module_lecture.common.exception.CustomException;
import com.jk.module_lecture.common.exception.ErrorCode;
import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import com.jk.module_lecture.review.entity.Review;
import com.jk.module_lecture.review.repository.ReviewRepository;
import com.jk.module_lecture.review.service.dto.ReviewListDto;
import lombok.RequiredArgsConstructor;
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
    public Long createReview(Long lectureId, Long userId, String content, Integer star) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        Review review = Review.builder()
                .lecture(lecture)
                .userId(userId)
                .content(content)
                .star(star)
                .build();

        Review savedReview = reviewRepository.save(review);
        return savedReview.getReviewId();
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
     * 특정 강의에 대한 모든 리뷰 조회
     */
    public List<ReviewListDto> getReviewsByLectureId(Long lectureId) {
        List<Review> reviews = reviewRepository.findByLecture_LectureId(lectureId);
        return reviews.stream()
                .map(ReviewListDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자가 작성한 모든 리뷰 조회
     */
    public List<ReviewListDto> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream()
                .map(ReviewListDto::toDto)
                .collect(Collectors.toList());
    }
}