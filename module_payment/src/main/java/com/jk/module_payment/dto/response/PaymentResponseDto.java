package com.jk.module_payment.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponseDto(
        Long paymentNum,
        Long orderNum,
        Long buyerNum,
        BigDecimal price,
        LocalDateTime createdAt
) {
}
