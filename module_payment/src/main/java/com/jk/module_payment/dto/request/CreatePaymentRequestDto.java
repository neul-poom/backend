package com.jk.module_payment.dto.request;

import lombok.Builder;

@Builder
public record CreatePaymentRequestDto(
        Long orderNum
) {
}
