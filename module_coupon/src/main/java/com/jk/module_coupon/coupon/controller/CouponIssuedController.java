package com.jk.module_coupon.coupon.controller;

import com.jk.module_coupon.coupon.service.CouponIssuedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupon-issued")
public class CouponIssuedController {
    private final CouponIssuedService couponIssuedService;
}
