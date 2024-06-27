package com.jk.module_coupon.coupon.service;

import com.jk.module_coupon.coupon.domain.CouponIssued;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.dto.request.CouponIssuedRequestDto;
import com.jk.module_coupon.coupon.dto.response.CouponIssuedResponseDto;
import com.jk.module_coupon.coupon.repository.CouponIssuedRepository;
import com.jk.module_coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponIssuedService {
    private final CouponRepository couponRepository;
    private final CouponIssuedRepository couponIssuedRepository;

    /*
     * 쿠폰 발급
     */
    @Transactional
    public CouponIssuedResponseDto issueCoupon(CouponIssuedRequestDto request) {
        // 쿠폰과 유저 검증 로직 추가
        CouponIssued issued = new CouponIssued();
        issued.setCouponId(request.couponId());
        issued.setUserId(request.userId());
        issued.setIssuedAt(LocalDateTime.now());

        CouponIssued saved = couponIssuedRepository.save(issued);

        return CouponIssuedResponseDto.fromEntity(saved);
    }
}
