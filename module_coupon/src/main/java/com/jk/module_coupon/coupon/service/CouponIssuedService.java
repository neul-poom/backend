package com.jk.module_coupon.coupon.service;

import com.jk.module_coupon.common.exception.CustomException;
import com.jk.module_coupon.common.exception.ErrorCode;
import com.jk.module_coupon.coupon.domain.Coupon;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponIssuedService {
    private final CouponRepository couponRepository;
    private final CouponIssuedRepository couponIssuedRepository;

    /*
     * 일반 쿠폰 발급
     */
    @Transactional
    public CouponIssuedResponseDto issueCoupon(CouponIssuedRequestDto request) {
        // 쿠폰 ID로 쿠폰을 찾고, 존재하지 않으면 예외 처리
        Coupon coupon = couponRepository.findById(request.couponId())
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        // 사용자 ID로 사용자를 찾고, 존재하지 않으면 예외 처리
        // User user = userRepository.findById(request.getUserId())
        //         .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        CouponIssued issued = CouponIssued.builder()
                .coupon(coupon)
                .userId(request.userId())
                .issuedAt(LocalDateTime.now())
                .build();

        CouponIssued saved = couponIssuedRepository.save(issued);

        return CouponIssuedResponseDto.fromEntity(saved);
    }

    /*
     * 선착순 쿠폰 발급
     */
    @Transactional
    public CouponIssuedResponseDto issueFirstComeCoupon(CouponIssuedRequestDto request) {
        // 쿠폰 ID로 쿠폰을 찾고, 존재하지 않으면 예외 처리
        Coupon coupon = couponRepository.findById(request.couponId())
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        // 발급 가능한 쿠폰 수량 확인
        if (coupon.getIssuedQuantity() >= coupon.getMaxQuantity()) {
            throw new CustomException(ErrorCode.COUPON_ISSUE_LIMIT_EXCEEDED);
        }

        // 새로운 쿠폰 발급
        CouponIssued issued = CouponIssued.builder()
                .coupon(coupon)
                .userId(request.userId())
                .issuedAt(LocalDateTime.now())
                .build();

        CouponIssued saved = couponIssuedRepository.save(issued);

        // 발급된 쿠폰 수량 증가
        coupon.incrementIssuedQuantity();

        // 쿠폰 업데이트
        couponRepository.save(coupon);

        return CouponIssuedResponseDto.fromEntity(saved);
    }

    /*
     * 유저의 쿠폰 발급 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CouponIssuedResponseDto> listUserCoupons(Long userId) {
        List<CouponIssued> issueds = couponIssuedRepository.findByUserId(userId);
        return issueds.stream().map(CouponIssuedResponseDto::fromEntity).collect(Collectors.toList());
    }

    /*
     * 특정 쿠폰의 발급 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CouponIssuedResponseDto> listCouponIssued(Long couponId) {
        List<CouponIssued> issueds = couponIssuedRepository.findByCoupon_CouponId(couponId);
        if (issueds.isEmpty()) {
            throw new CustomException(ErrorCode.COUPON_LIST_NOT_FOUND);
        }
        return issueds.stream().map(CouponIssuedResponseDto::fromEntity).collect(Collectors.toList());
    }

    /*
     * 발급된 쿠폰 취소
     */
    @Transactional
    public void cancelCouponIssued(Long issuedId) {
        if (!couponIssuedRepository.existsById(issuedId)) {
            throw new CustomException(ErrorCode.ISSUEDID_NOT_FOUND);
        }
        couponIssuedRepository.deleteById(issuedId);
    }
}
