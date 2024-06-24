package com.jk.module_coupon.coupon.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
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
    private int discountRate;

    @Column(name = "max_quantity")
    private Long maxQuantity;

    @Column(name = "issued_quantity")
    private Long issuedQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Builder
    public Coupon(String name, String couponCode, int discountRate, Long maxQuantity, Long issuedQuantity, LocalDateTime expiresAt) {
        this.name = name;
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.maxQuantity = maxQuantity;
        this.issuedQuantity = issuedQuantity;
        this.expiresAt = expiresAt;
    }


}
