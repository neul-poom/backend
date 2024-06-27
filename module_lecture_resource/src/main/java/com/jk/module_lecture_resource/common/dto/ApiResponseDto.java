package com.jk.module_lecture_resource.common.dto;

import org.springframework.http.HttpStatus;

public record ApiResponseDto<T>(HttpStatus status, String message, T data) {
}
