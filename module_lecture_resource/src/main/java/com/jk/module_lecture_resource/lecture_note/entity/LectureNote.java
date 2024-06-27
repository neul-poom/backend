package com.jk.module_lecture_resource.lecture_note.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "lecture_note")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class LectureNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", updatable = false)
    private Long noteId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public LectureNote(Long lectureId, String title, String fileUrl) {
        this.lectureId = lectureId;
        this.title = title;
        this.fileUrl = fileUrl;
    }

    public void update(String title, String fileUrl) {
        this.title = title;
        this.fileUrl = fileUrl;
    }
}