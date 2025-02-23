package com.jk.module_coupon.coupon.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", updatable = false)
    private Long couponId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate;

    @Column(name = "max_quantity")
    private Long maxQuantity;

    @Column(name = "issued_quantity")
    private Long issuedQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "coupon_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponTypeEnum couponType;

    @Builder
    public Coupon(String name, String couponCode, Integer discountRate, Long maxQuantity, Long issuedQuantity, LocalDateTime expiresAt, CouponTypeEnum couponType) {
        this.name = name;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.maxQuantity = maxQuantity;
        this.issuedQuantity = issuedQuantity;
        this.expiresAt = expiresAt;
        this.status = true;
        this.couponType = couponType;
    }

    public void update(String name, String couponCode, Integer discountRate, Long maxQuantity, Long issuedQuantity, LocalDateTime expiresAt, CouponTypeEnum couponType) {
        if (name != null) {
            this.name = name;
        }
        if (couponCode != null) {
            this.couponCode = couponCode;
        }
        if (discountRate != null) {
            this.discountRate = discountRate;
        }
        if (maxQuantity != null) {
            this.maxQuantity = maxQuantity;
        }
        if (expiresAt != null) {
            this.expiresAt = expiresAt;
        }
        if (couponType != null) {
            this.couponType = couponType;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = false;
        this.updatedAt = LocalDateTime.now();
    }

    // 발급된 수량 증가 메서드
    public void incrementIssuedQuantity() {
        if (this.issuedQuantity == null) {
            this.issuedQuantity = 0L;
        }
        this.issuedQuantity += 1;
    }
}
