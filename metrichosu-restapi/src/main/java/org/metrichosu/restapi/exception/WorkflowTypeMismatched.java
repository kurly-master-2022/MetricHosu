package org.metrichosu.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : jbinchoo
 * @since : 2022-08-22
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "이 워크플로 타입에 지원되지 않습니다.")
public class WorkflowTypeMismatched extends RuntimeException {

    public WorkflowTypeMismatched() {
        super();
    }
}
