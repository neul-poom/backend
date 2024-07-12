package com.jk.module_order.entity;

import com.jk.module_order.dto.request.CreateOrderRequest;
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
@Table(name = "orders") //  엔티티 클래스와 데이터베이스 테이블의 매핑을 명시적으로 정의
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 자동 생성
@EntityListeners(AuditingEntityListener.class) // 엔터티 생성, 수정 시간 자동 감지 및 기록
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_num")
    private Long orderNum;

    @Column(name = "buyer_num", nullable = false)
    private Long buyerNum;

    @Column(name = "lecture_num", nullable = false)
    private Long lectureNum;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Order(Long buyerNum, Long lectureNum, BigDecimal price, OrderStatus status) {
        this.buyerNum = buyerNum;
        this.lectureNum = lectureNum;
        this.price = price;
        this.status = status;
    }

    public static Order create(CreateOrderRequest createOrderRequest, Long userId) {
        return Order.builder()
                .buyerNum(userId)
                .lectureNum(createOrderRequest.lectureNum())
                .price(createOrderRequest.price())
                .status(OrderStatus.INITIATED)
                .build();
    }

    public enum OrderStatus {
        INITIATED,
        IN_PROGRESS,
        COMPLETED,
        FAILED_CUSTOMER, // 주문 실패
        CANCELED // 주문 취소
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void delete() {
        this.status = OrderStatus.CANCELED;
    }

    public void failed() { this.status = OrderStatus.FAILED_CUSTOMER; }

    public void complete() { this.status = OrderStatus.COMPLETED; }

}
