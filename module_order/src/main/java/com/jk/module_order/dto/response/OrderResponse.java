package com.jk.module_order.dto.response;

import com.jk.module_order.entity.Order;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long orderNum,
        Long buyerNum,
        Long lectureNum,
        BigDecimal price,
        Order.OrderStatus status
) {
}
