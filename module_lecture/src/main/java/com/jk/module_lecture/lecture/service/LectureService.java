package com.jk.module_lecture.lecture.service;

import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;

    /**
     * 강의 등록
     */
    @Transactional
    public Long create(String title, String description, Long teacherId, BigDecimal price) {
        Lecture lecture = Lecture.builder()
                .title(title)
                .description(description)
                .teacherId(teacherId)
                .price(price)
                .build();

        Lecture savedLecture = lectureRepository.save(lecture);

        return savedLecture.getLectureId();
    }

    /**
     * 강의 수정
     */
    @Transactional
    public Long update(Long lectureId, String title, String description, Long teacherId, BigDecimal price) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomEx)

        Lecture savedLecture = lectureRepository.save(lecture);

        return savedLecture.getLectureId();
    }

}

