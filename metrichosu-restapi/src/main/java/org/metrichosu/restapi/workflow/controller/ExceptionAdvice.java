package org.metrichosu.restapi.workflow.controller;

import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.dto.validation.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author : jbinchoo
 * @since : 2022-08-21
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Invalid method argument found.", ex);
        return ValidationError.badRequest(ex, headers, status);
    }
}
