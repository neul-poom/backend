package com.jk.module_user.user.entity;

import com.jk.module_user.user.dto.request.SignupRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "role", columnDefinition = "varchar(10) default 'USER'")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "balance") // 잔액
    private String balance;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", columnDefinition = "varchar(1) default 'Y'") // soft delete
    private String status;

    @Builder
    public User(String username, String password, String email, UserRoleEnum role, String status, String profileImg, String balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.profileImg = profileImg;
        this.balance = balance;
    }

}
