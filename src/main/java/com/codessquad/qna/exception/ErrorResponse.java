package com.codessquad.qna.exception;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ErrorResponse {

    private String code;
    private String message;
    private LocalDateTime time;
    private List<FiledError> errors;
    private UUID logId;

    private ErrorResponse(String code, String message, LocalDateTime time, List<FiledError> errors, UUID logId) {
        this.code = code;
        this.message = message;
        this.time = time;
        this.errors = errors;
        this.logId = logId;
    }

    public static ErrorResponse of (ErrorCode errorCode, UUID uuid){
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getReason(),
                LocalDateTime.now(),
                new ArrayList<>(),
                uuid
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult, UUID logId) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getReason(),
                LocalDateTime.now(),
                FiledError.of(bindingResult),
                logId
        );
    }
    public static ErrorResponse of(ErrorCode errorCode, List<FiledError> bindingResult, UUID logId) {
        return new ErrorResponse(
                errorCode.name(),
                errorCode.getReason(),
                LocalDateTime.now(),
                bindingResult,
                logId
        );
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException ex,UUID logId){
        String value = (ex.getValue() == null)? "":ex.getValue().toString();
        List<FiledError> errors = Arrays.asList(FiledError.of(ex.getName(), value, ex.getErrorCode()));
        return ErrorResponse.of(ErrorCode.BAD_REQUEST,errors,logId);
    }

    public static ErrorResponse of(ErrorCode errorCode, UUID logId, HttpStatus status) {
        return ErrorResponse.of(errorCode, logId, status);
    }


    //에러 이유 잡기
    public static class FiledError {
        private final String filed; // 필드
        private final String value;  // 발생 원인의 값
        private final String reason; //메시지

        protected FiledError(String filed, String value, String reason) {
            this.filed = filed;
            this.value = value;
            this.reason = reason;
        }

        public static FiledError of(String filed, String value, String reason) {
            return new FiledError(filed, value, reason);
        }

        public static FiledError of(FieldError fieldError) {
            return FiledError.of(fieldError.getField(),
                    (fieldError.getRejectedValue() == null) ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage()
                    );
        }

        private static List<FiledError> of (BindingResult bindingResult){
            return bindingResult.getFieldErrors().stream()
                    .map(FiledError::of)
                    .collect(Collectors.toList());
        }

    }

}
