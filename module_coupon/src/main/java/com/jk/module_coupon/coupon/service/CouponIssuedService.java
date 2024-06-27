package com.jk.module_coupon.coupon.service;

import com.jk.module_coupon.coupon.repository.CouponIssuedRepository;
import com.jk.module_coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssuedService {
    private final CouponRepository couponRepository;
    private final CouponIssuedRepository couponIssuedRepository;
}
