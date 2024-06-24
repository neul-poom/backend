package com.jk.module_coupon.coupon.controller;

import com.jk.module_coupon.coupon.domain.Coupon;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.dto.request.CouponUpdateRequestDto;
import com.jk.module_coupon.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    /*
     * 단일 쿠폰 조회
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        return ResponseEntity.ok(coupon);
    }

    /*
     * 쿠폰 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<Coupon>> listCoupons(@RequestParam int page, @RequestParam int size) {
        List<Coupon> coupons = couponService.listCoupons(page, size);
        return ResponseEntity.ok(coupons);
    }

    /*
     * 쿠폰 수정
     */
    @PutMapping("/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable Long couponId, @Valid @RequestBody CouponUpdateRequestDto request) {
        couponService.updateCoupon(couponId, request);
        return ResponseEntity.ok().build();
    }


}
