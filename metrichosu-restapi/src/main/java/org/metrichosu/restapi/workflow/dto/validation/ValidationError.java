package org.metrichosu.restapi.workflow.dto.validation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * @author : jbinchoo
 * @since : 2022-08-21
 */
public class ValidationError {

    private String parameterName;
    private String message;

    public ValidationError(MethodArgumentNotValidException ex) {
        this.parameterName = ex.getParameter().getParameterName();
        this.message = ex.getMessage();
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<Object> badRequest(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(new ValidationError(ex), headers, status);
    }
}
