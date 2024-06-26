package com.jk.module_lecture_resource.multimedia.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "multimedia")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", updatable = false)
    private Long fileId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    @Builder
    public Multimedia(Long lectureId, String fileUrl) {
        this.lectureId = lectureId;
        this.fileUrl = fileUrl;
    }

    public void update(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}