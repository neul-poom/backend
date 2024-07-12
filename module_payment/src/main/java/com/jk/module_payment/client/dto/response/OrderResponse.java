package com.jk.module_payment.client.dto.response;

import com.jk.module_payment.common.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long orderNum,
        Long buyerNum,
        Long lectureNum,
        BigDecimal price,
        OrderStatus status
) {

}
