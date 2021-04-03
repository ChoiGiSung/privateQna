package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    BAD_REQUEST("990400",HttpStatus.BAD_REQUEST,"잘못된 입력값"),
    UNAUTHORIZED("990401",HttpStatus.UNAUTHORIZED,"인증 실패"),
    NOT_FOUND("990402",HttpStatus.NOT_FOUND,"권한 없음"),
    UNKNOWN("990403",HttpStatus.INTERNAL_SERVER_ERROR,"찾을 수 없음");

    private final String code;
    private final HttpStatus status;
    private final String reason;

    ErrorCode(String code, HttpStatus status, String reason) {
        this.code = code;
        this.status = status;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
