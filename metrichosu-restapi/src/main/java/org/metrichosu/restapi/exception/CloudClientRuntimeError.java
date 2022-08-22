package org.metrichosu.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author : jbinchoo
 * @since : 2022-08-22
 */
public class CloudClientRuntimeError extends ResponseStatusException {
    private static final String DEFAULT_MESSAGE = "주어진 표현을 AWS에서 거부했습니다.";

    public CloudClientRuntimeError() {
        this(DEFAULT_MESSAGE);
    }

    public CloudClientRuntimeError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE + message);
    }

    public CloudClientRuntimeError(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE + ' ' + message, cause);
    }
}
