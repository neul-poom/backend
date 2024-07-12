package com.jk.module_payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter // 각 필드의 getter 메소드 자동 생성
@Entity // 이 클래스가 JPA 엔터티임을 나타냄(데이터 베이 테이블에 매핑됨)
@Table(name = "payment") //  엔티티 클래스와 데이터베이스 테이블의 매핑을 명시적으로 정의
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성
@EntityListeners(AuditingEntityListener.class) // 엔터티 생성, 수정 시간 자동 감지 및 기록
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_num")
    private Long paymentNum;

    @Column(name = "order_num", nullable = false)
    private Long orderNum;

    @Column(name = "buyer_num", nullable = false)
    private Long buyerNum;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name= "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public Payment(Long orderNum, Long buyerNum, BigDecimal price) {
        this.orderNum = orderNum;
        this.buyerNum = buyerNum;
        this.price = price;
    }

    public static Payment create(Long orderNum, Long buyerNum, BigDecimal price) {
        return Payment.builder()
                .orderNum(orderNum)
                .buyerNum(buyerNum)
                .price(price)
                .build();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
