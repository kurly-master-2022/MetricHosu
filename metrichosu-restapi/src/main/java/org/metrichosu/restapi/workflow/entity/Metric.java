package org.metrichosu.restapi.workflow.entity;

import lombok.*;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ToString
@Getter
@Builder
@AllArgsConstructor
public class Metric {

    private final String id;
    private final String name;
    private final String sourceUri;
    private final MetricType metricType;
}
