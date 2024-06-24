package com.jk.module_coupon.coupon.controller;

import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    /*
     * 쿠폰 등록
     */
    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCreateRequestDto request) {
        Long couponId = couponService.createCoupon(request);

        return ResponseEntity.created(URI.create("/api/v1/coupons/" + couponId)).build();
    }
}
