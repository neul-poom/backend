package com.jk.module_coupon.coupon.repository;

import com.jk.module_coupon.coupon.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    // 유효한 쿠폰 목록 조회
    Page<Coupon> findByExpiresAtAfterAndStatusIsTrue(LocalDateTime dateTime, Pageable pageable);
    // 만료된 쿠폰 목록 조회
    Page<Coupon> findByExpiresAtBeforeAndStatusTrue(LocalDateTime dateTime, Pageable pageable);
}
