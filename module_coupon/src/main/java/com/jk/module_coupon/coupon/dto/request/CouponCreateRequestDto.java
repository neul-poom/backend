package com.jk.module_coupon.coupon.dto.request;

import com.jk.module_coupon.coupon.domain.CouponTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CouponCreateRequestDto (
    @NotNull
    String name,
    @NotNull
    String couponCode,
    @NotNull
    Integer discountRate,
    Long maxQuantity,
    Long issuedQuantity,
    @NotNull
    LocalDateTime expiresAt,
    CouponTypeEnum couponType
) {}
