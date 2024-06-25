package com.jk.module_lecture.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponseDto<T>(HttpStatus status, String message, T data) {
}
