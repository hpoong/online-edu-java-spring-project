package com.hopoong.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<Object> NotFoundExceptionResponse(NotFoundException ex) {
        return new ResponseEntity<>(ex.getErrmsg(), ex.getHttpStatus());
    }
}
