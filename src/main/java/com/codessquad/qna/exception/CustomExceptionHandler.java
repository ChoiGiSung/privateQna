package com.codessquad.qna.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;


@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    private UUID createLogId(Exception ex) {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> sample(BusinessException ex) {
        return new ResponseEntity<>(ErrorResponse.of(ex.getErrorCode(), createLogId(ex)),ex.getErrorCode().getStatus());
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public String loginException() {
        return "/user/login";
    }

    @ExceptionHandler(IllegalUserUpdateException.class)
    public String userUpdateException(IllegalUserUpdateException e) {
        String[] splitMessage = e.getMessage().split(":");
        return "redirect:/users/" + splitMessage[0] + "/form";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    public String noSameUser() {
        return "redirect:/";
    }

    @ExceptionHandler(NoSearchObjectException.class)
    public String noSearchObject(NoSearchObjectException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }
}
