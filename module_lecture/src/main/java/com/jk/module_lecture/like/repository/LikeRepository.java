package com.jk.module_lecture.like.repository;

import com.jk.module_lecture.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndLecture_LectureId(Long userId, Long lectureId);
}
