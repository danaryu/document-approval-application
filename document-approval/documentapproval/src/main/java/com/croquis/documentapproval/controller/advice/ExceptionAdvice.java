package com.croquis.documentapproval.controller.advice;

import com.croquis.documentapproval.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    // TODO 공통 Exception 처리
    @ExceptionHandler(NotFoundException.class)
    public String notFoundExceptionHandler(NotFoundException e) {
        log.error("###### NotFoundException ###### ");
        return "home";
    }

}
