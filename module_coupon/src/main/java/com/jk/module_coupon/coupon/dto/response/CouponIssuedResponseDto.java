package com.jk.module_coupon.coupon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jk.module_coupon.coupon.domain.CouponIssued;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CouponIssuedResponseDto(
        Long issuedId,
        Long couponId,
        Long userId,
        LocalDateTime issuedAt
) {
    public static CouponIssuedResponseDto fromEntity(CouponIssued issued) {
        return CouponIssuedResponseDto.builder()
                .issuedId(issued.getIssuedId())
                .couponId(issued.getCoupon().getCouponId())
                .userId(issued.getUserId())
                .issuedAt(issued.getIssuedAt())
                .build();
    }
}
