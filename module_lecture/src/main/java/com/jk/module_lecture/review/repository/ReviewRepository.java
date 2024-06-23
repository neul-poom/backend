package com.jk.module_lecture.review.repository;

import com.jk.module_lecture.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByLecture_LectureId(Long lectureId);

    List<Review> findByUserId(Long userId);

    List<Review> findByLecture_LectureIdAndUserId(Long lectureId, Long userId);
}