package com.jk.module_order.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponseDto<T>(HttpStatus status, String message, T data) {
}
