package com.jk.module_coupon.coupon.controller;


import com.jk.module_coupon.common.dto.ApiResponseDto;
import com.jk.module_coupon.coupon.domain.Coupon;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.dto.request.CouponUpdateRequestDto;
import com.jk.module_coupon.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    /*
     * 쿠폰 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<Long>> createCoupon(@Valid @RequestBody CouponCreateRequestDto request) {
        Long couponId = couponService.createCoupon(request);

        return ResponseEntity
                .created(URI.create("/api/v1/coupons/" + couponId))
                .body(new ApiResponseDto<>(HttpStatus.CREATED, "쿠폰이 등록되었습니다.", couponId));
    }

    /*
     * 단일 쿠폰 조회
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<ApiResponseDto<Coupon>> getCoupon(@PathVariable Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "쿠폰 상세정보 조회", coupon));
    }

    /*
     * 쿠폰 목록 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Coupon>>> listCoupons(@RequestParam int page, @RequestParam int size) {
        List<Coupon> coupons = couponService.listCoupons(page, size);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "쿠폰 전체 목록 조회", coupons));
    }

    /*
     * 쿠폰 수정
     */
    @PutMapping("/{couponId}")
    public ResponseEntity<ApiResponseDto<Coupon>> updateCoupon(@PathVariable Long couponId, @Valid @RequestBody CouponUpdateRequestDto request) {
        couponService.updateCoupon(couponId, request);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "쿠폰이 업데이트되었습니다.", null));
    }

    /*
     * 쿠폰 삭제
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.ok().body(new ApiResponseDto<>(HttpStatus.OK, "강의가 삭제됐습니다.", null));
    }

    /*
     * 유효한 쿠폰 목록 조회
     */
    @GetMapping("/valid")
    public ResponseEntity<ApiResponseDto<List<Coupon>>> listValidCoupons(@RequestParam int page, @RequestParam int size) {
        List<Coupon> coupons = couponService.listValidCoupons(page, size);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "유효한 쿠폰 목록 조회", coupons));
    }

    /*
     * 만료된 쿠폰 목록 조회
     */
    @GetMapping("/expired")
    public ResponseEntity<ApiResponseDto<List<Coupon>>> listExpiredCoupons(@RequestParam int page, @RequestParam int size) {
        List<Coupon> coupons = couponService.listExpiredCoupons(page, size);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "만료된 쿠폰 목록 조회", coupons));
    }


}
