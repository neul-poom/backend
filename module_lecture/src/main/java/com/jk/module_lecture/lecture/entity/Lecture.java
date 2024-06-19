package com.jk.module_lecture.lecture.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter // 각 필드의 getter 메소드 자동 생성
@Entity // 이 클래스가 JPA 엔터티임을 나타냄(데이터 베이 테이블에 매핑됨)
@Table(name = "lecture") //  엔티티 클래스와 데이터베이스 테이블의 매핑을 명시적으로 정의
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 기본 생성자 자동 생성
@EntityListeners(AuditingEntityListener.class) // 엔터티 생성, 수정 시간 자동 감지 및 기록
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id", updatable = false)
    private Long lectureId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Builder
    public Lecture(String title, String description, Long teacherId, BigDecimal price) {
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.status = true;
    }

}