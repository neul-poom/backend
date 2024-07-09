package com.jk.module_lecture.lecture.service;

import com.jk.module_lecture.client.LectureResourceClient;
import com.jk.module_lecture.common.exception.CustomException;
import com.jk.module_lecture.common.exception.ErrorCode;
import com.jk.module_lecture.lecture.dto.request.LectureCreateRequestDto;
import com.jk.module_lecture.lecture.dto.response.*;
import com.jk.module_lecture.lecture.entity.Lecture;
import com.jk.module_lecture.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureResourceClient lectureResourceClient;
    /**
     * 강의 등록
     */
    @Transactional
    public LectureCreateResponseDto create(Long teacherId, LectureCreateRequestDto lectureCreateRequestDto) {
        Lecture lecture = Lecture.builder()
                .title(lectureCreateRequestDto.title())
                .description(lectureCreateRequestDto.description())
                .teacherId(teacherId)
                .price(lectureCreateRequestDto.price())
                .build();

        Lecture savedLecture = lectureRepository.save(lecture);

        lectureResourceClient.createNote(
                lectureCreateRequestDto.file(),
                savedLecture.getLectureId(),
                savedLecture.getTitle()
        );

        lectureResourceClient.createVideo(
                lectureCreateRequestDto.video(),
                savedLecture.getLectureId()
        );

        return LectureCreateResponseDto.builder()
                .lectureId(savedLecture.getLectureId())
                .title(savedLecture.getTitle())
                .description(savedLecture.getDescription())
                .teacherId(savedLecture.getTeacherId())
                .price(savedLecture.getPrice())
                .build();
    }

    /**
     * 강의 수정
     */
    @Transactional
    public LectureUpdateResponseDto update(Long teacherId, Long lectureId, String title, String description, BigDecimal price) {

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        checkLectureOwner(teacherId, lecture);

        lecture.update(title, description, price);
        lectureRepository.save(lecture);

        return LectureUpdateResponseDto.toDto(lecture);
    }

    /**
     * 강의 소유자 확인
     */
    @Transactional
    public void checkLectureOwner(Long teacherId, Lecture lecture) {
        if (!lecture.getTeacherId().equals(teacherId)) {
            throw new CustomException(ErrorCode.USER_NOT_MATCH);
        }
    }

    /**
     * 강의 상세 조회
     */
    public LectureDetailResponseDto getLectureInfoById(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        String file = lectureResourceClient.getNoteUrl(lecture.getLectureId());
        String video = lectureResourceClient.getVideoRul(lecture.getLectureId());

        return LectureDetailResponseDto.toDto(lecture, file, video);
    }

    /**
     * 전체 강의 조회 (페이징)
     */
    public Page<LectureListResponseDto> getAllLectures(Pageable pageable) {
        Page<Lecture> lecturePage = lectureRepository.findAll(pageable);
        return lecturePage.map(LectureListResponseDto::toDto);
    }

    /**
     * 강의 삭제
     */
    @Transactional
    public LectureDeleteResponseDto delete(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        lecture.deactivate();
        lectureRepository.save(lecture);

        return LectureDeleteResponseDto.toDto(lecture);
    }
}

