package com.jk.module_coupon.coupon.service;

import com.jk.module_coupon.coupon.domain.Coupon;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    /*
     * 쿠폰 등록
     */
    @Transactional
    public Long createCoupon(CouponCreateRequestDto request) {
        Coupon coupon = Coupon.builder()
                .name(request.name())
                .couponCode(request.couponCode())
                .discountRate(request.discountRate())
                .maxQuantity(request.maxQuantity())
                .issuedQuantity(request.issuedQuantity() != null ? request.issuedQuantity() : 0L)
                .expiresAt(request.expiresAt())
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getCouponId();
    }

}
