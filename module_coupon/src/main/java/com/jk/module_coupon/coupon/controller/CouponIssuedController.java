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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*
     * 유저의 쿠폰 발급 목록 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<CouponIssuedResponseDto>>> listUserCoupons(@PathVariable Long userId) {
        List<CouponIssuedResponseDto> issueds = couponIssuedService.listUserCoupons(userId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "유저의 쿠폰 발급 목록입니다.", issueds));
    }

    /*
     * 특정 쿠폰의 발급 목록 조회
     */
    @GetMapping("/coupon/{couponId}")
    public ResponseEntity<ApiResponseDto<List<CouponIssuedResponseDto>>> listCouponIssued(@PathVariable Long couponId) {
        List<CouponIssuedResponseDto> issueds = couponIssuedService.listCouponIssued(couponId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "특정 쿠폰의 발급 내역입니다.", issueds));
    }

    /*
     * 발급된 쿠폰 취소
     */
    @DeleteMapping("/{issuedId}")
    public ResponseEntity<ApiResponseDto<Void>> cancelCouponIssued(@PathVariable Long issuedId) {
        couponIssuedService.cancelCouponIssued(issuedId);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK, "쿠폰 발급이 취소되었습니다.", null));
    }

}
