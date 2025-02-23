package com.jk.module_coupon.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // ------ 4xx ------
    NOT_FOUND(HttpStatus.BAD_REQUEST, "요청사항을 찾지 못했습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 쿠폰을 찾을 수 없습니다."),
    COUPON_LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰 발급 목록이 존재하지 않습니다."),
    ISSUEDID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 쿠폰 발급 ID 입니다."),
    COUPON_ISSUE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "발급 가능한 수량을 초과하였습니다."),

    // ------ 5xx ------
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
