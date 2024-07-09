package com.jk.module_lecture_resource.multimedia.service;

import com.jk.module_lecture_resource.common.exception.CustomException;
import com.jk.module_lecture_resource.common.exception.ErrorCode;
import com.jk.module_lecture_resource.common.s3.service.S3Service;
import com.jk.module_lecture_resource.lecture_note.entity.LectureNote;
import com.jk.module_lecture_resource.multimedia.dto.request.MultimediaUpdateRequestDto;
import com.jk.module_lecture_resource.multimedia.dto.response.MultimediaResponseDto;
import com.jk.module_lecture_resource.multimedia.entity.Multimedia;
import com.jk.module_lecture_resource.multimedia.repository.MultimediaRepository;
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
public class MultimediaService {
    private final MultimediaRepository multimediaRepository;
    private final S3Service s3Service;

    /**
     * 멀티미디어 파일 등록
     */
    @Transactional
    public MultimediaResponseDto create(Long lectureId, MultipartFile file) {
        String fileUrl;
        try {
            String fileName = "multimedia/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            fileUrl = s3Service.uploadFile(fileName, file);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        Multimedia multimedia = Multimedia.builder()
                .lectureId(lectureId)
                .fileUrl(fileUrl)
                .build();

        Multimedia savedMultimedia = multimediaRepository.save(multimedia);

        return MultimediaResponseDto.builder()
                .fileId(savedMultimedia.getFileId())
                .lectureId(savedMultimedia.getLectureId())
                .fileUrl(savedMultimedia.getFileUrl())
                .build();
    }

    public String getVideoDetails(Long lectureId) {

        Multimedia multimedia = multimediaRepository.findByLectureId(lectureId);

        return multimedia.getFileUrl();
    }

    /**
     * 멀티미디어 파일 수정
     */
    @Transactional
    public MultimediaResponseDto update(Long fileId, MultimediaUpdateRequestDto request) {
        Multimedia multimedia = multimediaRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.MULTIMEDIA_NOT_FOUND));

        String fileUrl = multimedia.getFileUrl();
        if (request.file() != null) {
            try {
                // 기존 파일 삭제
                s3Service.deleteFile(fileUrl);

                String fileName = "multimedia/" + UUID.randomUUID() + "-" + request.file().getOriginalFilename();
                fileUrl = s3Service.uploadFile(fileName, request.file());
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        multimedia.update(fileUrl);
        multimediaRepository.save(multimedia);

        return MultimediaResponseDto.builder()
                .fileId(multimedia.getFileId())
                .lectureId(multimedia.getLectureId())
                .fileUrl(multimedia.getFileUrl())
                .build();
    }

    /**
     * 멀티미디어 파일 삭제
     */
    @Transactional
    public void delete(Long fileId) {
        Multimedia multimedia = multimediaRepository.findById(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.MULTIMEDIA_NOT_FOUND));

        // 파일 삭제
        s3Service.deleteFile(multimedia.getFileUrl());

        multimediaRepository.delete(multimedia);
    }
}