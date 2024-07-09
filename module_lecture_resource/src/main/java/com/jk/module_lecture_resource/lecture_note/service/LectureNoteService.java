package com.jk.module_lecture_resource.lecture_note.service;

import com.jk.module_lecture_resource.common.exception.CustomException;
import com.jk.module_lecture_resource.common.exception.ErrorCode;
import com.jk.module_lecture_resource.common.s3.service.S3Service;
import com.jk.module_lecture_resource.lecture_note.dto.response.LectureNoteResponseDto;
import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import com.jk.module_lecture_resource.lecture_note.repository.LectureNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureNoteService {
    private final LectureNoteRepository lectureNoteRepository;
    private final S3Service s3Service;

    /**
     * 강의 노트 등록
     */
    @Transactional
    public LectureNoteResponseDto create(Long lectureId, String title, MultipartFile file) {
        String fileUrl;
        try {
            String fileName = "lecture-notes/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            fileUrl = s3Service.uploadFile(fileName, file);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        LectureNote lectureNote = LectureNote.builder()
                .lectureId(lectureId)
                .title(title)
                .fileUrl(fileUrl)
                .build();

        LectureNote savedLectureNote = lectureNoteRepository.save(lectureNote);

        return LectureNoteResponseDto.builder()
                .noteId(savedLectureNote.getNoteId())
                .lectureId(savedLectureNote.getLectureId())
                .title(savedLectureNote.getTitle())
                .fileUrl(savedLectureNote.getFileUrl())
                .build();
    }

    /**
     * 강의 노트 조회
     */
    public String getNoteDetails(Long lectureId) {

        LectureNote lectureNote = lectureNoteRepository.findByLectureId(lectureId);

        return lectureNote.getFileUrl();
    }

    /**
     * 강의 노트 수정
     */
    @Transactional
    public LectureNoteResponseDto update(Long noteId, String title, MultipartFile file) {
        LectureNote lectureNote = lectureNoteRepository.findById(noteId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTE_NOT_FOUND));

        String fileUrl = lectureNote.getFileUrl();
        if (file != null) {
            try {
                // 기존 파일 삭제
                s3Service.deleteFile(fileUrl);

                String fileName = "lecture-notes/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
                fileUrl = s3Service.uploadFile(fileName, file);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        lectureNote.update(title, fileUrl);
        lectureNoteRepository.save(lectureNote);

        return LectureNoteResponseDto.builder()
                .noteId(lectureNote.getNoteId())
                .lectureId(lectureNote.getLectureId())
                .title(lectureNote.getTitle())
                .fileUrl(lectureNote.getFileUrl())
                .build();
    }

    /**
     * 강의 노트 삭제
     */
    @Transactional
    public void delete(Long noteId) {
        LectureNote lectureNote = lectureNoteRepository.findById(noteId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTE_NOT_FOUND));

        // 파일 삭제
        s3Service.deleteFile(lectureNote.getFileUrl());

        lectureNoteRepository.delete(lectureNote);
    }
}