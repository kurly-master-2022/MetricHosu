package org.metrichosu.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : jbinchoo
 * @since : 2022-08-22
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "찾을 수 없는 메트릭입니다.")
public class MetricNotFoundException extends RuntimeException {

    public MetricNotFoundException() {
        super();
    }
}
