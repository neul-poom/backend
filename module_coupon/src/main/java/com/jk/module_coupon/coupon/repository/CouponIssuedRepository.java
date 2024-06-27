package com.jk.module_coupon.coupon.repository;

import com.jk.module_coupon.coupon.domain.CouponIssued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Long> {
    List<CouponIssued> findByUserId(Long userId);
}
