package com.jk.module_user.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity // 이 클래스가 JPA 엔터티임을 나타냄(데이터 베이 테이블에 매핑됨)
@Table(name = "users") //  엔티티 클래스와 데이터베이스 테이블의 매핑을 명시적으로 정의
@Getter // 각 필드의 getter 메소드 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성
@EntityListeners(AuditingEntityListener.class) // 엔터티 생성, 수정 시간 자동 감지 및 기록
public class User {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "role", columnDefinition = "varchar(10) default 'USER'") // 사용자, 관리자
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER; // 기본값 설정

    @Column(name = "balance") // 잔액
    private String balance;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status;

    @Builder
    public User(Long userId, String username, String password, String email, UserRoleEnum role, String profileImg, String balance, boolean status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role != null ? role : UserRoleEnum.USER; // 기본값 설정
        this.profileImg = profileImg;
        this.balance = balance;
        this.status = status;
    }

    public void deactivate() {
        this.status = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateUserInfo(String username, String email, String profileImg, String balance) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
        if (email != null && !email.isEmpty()) {
            this.email = email;
        }
        if (profileImg != null && !profileImg.isEmpty()) {
            this.profileImg = profileImg;
        }
        if (balance != null && !balance.isEmpty()) {
            this.balance = balance;
        }
        this.updatedAt = LocalDateTime.now();
    }

}
