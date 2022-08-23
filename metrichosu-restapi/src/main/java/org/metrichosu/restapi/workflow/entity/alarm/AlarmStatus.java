package org.metrichosu.restapi.workflow.entity.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
@ToString
@Getter
@Builder
@RequiredArgsConstructor
public class AlarmStatus {

    private final String id;
    private final AlarmStateValue state;
    private final String metricId;
}
