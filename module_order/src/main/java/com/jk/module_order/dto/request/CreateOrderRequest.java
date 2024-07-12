package com.jk.module_order.dto.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateOrderRequest(
        Long lectureNum,
        BigDecimal price
) {
}
