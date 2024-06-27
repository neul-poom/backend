package com.jk.module_coupon.coupon.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CouponIssuedRequestDto(
        Long couponId,
        Long userId
) {}
