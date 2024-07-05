package com.jk.module_coupon.coupon.service;

import com.jk.module_coupon.common.exception.CustomException;
import com.jk.module_coupon.common.exception.ErrorCode;
import com.jk.module_coupon.coupon.domain.Coupon;
import com.jk.module_coupon.coupon.dto.request.CouponCreateRequestDto;
import com.jk.module_coupon.coupon.dto.request.CouponUpdateRequestDto;
import com.jk.module_coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    /**
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
                .couponType(request.couponType())
                .build();

        Coupon savedCoupon = couponRepository.save(coupon);

        return savedCoupon.getCouponId();
    }

    /**
     * 단일 쿠폰 조회
     */
    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(
                () -> new CustomException(ErrorCode.COUPON_NOT_FOUND)
        );
    }

    /**
     * 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Coupon> listCoupons(int page, int size) {
        return couponRepository.findAll(PageRequest.of(page - 1, size)).getContent();
    }

    /**
     * 쿠폰 수정
     */
    @Transactional
    public void updateCoupon(Long couponId, CouponUpdateRequestDto request) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        coupon.update(request.name(),
                      request.couponCode(),
                      request.discountRate(),
                      request.maxQuantity(),
                      request.issuedQuantity(),
                      request.expiresAt(),
                      request.couponType());

        couponRepository.save(coupon);
    }

    /**
     * 쿠폰 삭제
     */
    @Transactional
    public void deleteCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        coupon.deactivate();
        couponRepository.save(coupon);
    }

    /**
     * 유효한 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Coupon> listValidCoupons(int page, int size) {
        return couponRepository.findByExpiresAtAfterAndStatusIsTrue(LocalDateTime.now(), PageRequest.of(page - 1, size)).getContent();
    }

    /**
     * 만료된 쿠폰 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Coupon> listExpiredCoupons(int page, int size) {
        return couponRepository.findByExpiresAtBeforeAndStatusTrue(LocalDateTime.now(), PageRequest.of(page - 1, size)).getContent();
    }


}
