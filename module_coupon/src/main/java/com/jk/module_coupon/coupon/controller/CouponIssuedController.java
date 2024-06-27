package com.jk.module_coupon.coupon.controller;

import com.jk.module_coupon.common.dto.ApiResponseDto;
import com.jk.module_coupon.coupon.domain.CouponIssued;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.dto.request.CouponIssuedRequestDto;
import com.jk.module_coupon.coupon.dto.response.CouponIssuedResponseDto;
import com.jk.module_coupon.coupon.service.CouponIssuedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupon-issued")
public class CouponIssuedController {
    private final CouponIssuedService couponIssuedService;

    /*
     * 쿠폰 발급
     */
    @PostMapping("/issue")
    public ResponseEntity<ApiResponseDto<CouponIssuedResponseDto>> issueCoupon(@RequestBody CouponIssuedRequestDto request) {
        CouponIssuedResponseDto issued = couponIssuedService.issueCoupon(request);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "쿠폰이 발급되었습니다.", issued));
    }

}
