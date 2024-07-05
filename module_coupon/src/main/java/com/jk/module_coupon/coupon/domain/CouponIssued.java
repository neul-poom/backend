package com.jk.module_coupon.coupon.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_issued")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CouponIssued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_id", updatable = false)
    private Long issuedId;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt;

    @Builder
    public CouponIssued(Coupon coupon, Long userId, LocalDateTime issuedAt) {
        this.coupon = coupon;
        this.userId = userId;
        this.issuedAt = LocalDateTime.now();
    }
}
