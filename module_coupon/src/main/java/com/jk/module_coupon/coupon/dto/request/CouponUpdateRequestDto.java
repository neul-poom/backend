package com.jk.module_coupon.coupon.dto.request;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Builder
public record CouponUpdateRequestDto (
        String name,
        String couponCode,
        Integer discountRate,
        Long maxQuantity,
        Long issuedQuantity,
        LocalDateTime expiresAt
){}