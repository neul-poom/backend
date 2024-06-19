package com.jk.module_lecture.lecture.service;

import com.jk.module_lecture.common.exception.CustomException;
import com.jk.module_lecture.common.exception.ErrorCode;
import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import com.jk.module_lecture.lecture.service.dto.response.LectureListResponseDto;
import com.jk.module_lecture.lecture.service.dto.response.LectureDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    public void update(Long lectureId, String title, String description, Long teacherId, BigDecimal price) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        checkLectureOwner(teacherId, lecture);

        lecture.update(title, description, price);
    }

    /**
     * 강의 소유자 확인
     */
    private void checkLectureOwner(Long teacherId, Lecture lecture) {
        if (!teacherId.equals(lecture.getTeacherId())) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
    }

    /**
     * 강의 상세 조회
     */
    public LectureDetailResponseDto getLectureInfoById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        return LectureDetailResponseDto.toDto(lecture);
    }

    /**
     * 전체 강의 조회
     */
    public List<LectureListResponseDto> getAllLectures() {
        List<Lecture> lectures = lectureRepository.findAll();

        return lectures.stream()
                .map(LectureListResponseDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 강의 삭제
     */
    @Transactional
    public void delete(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        lecture.deactivate();
        lectureRepository.save(lecture);
    }
}

