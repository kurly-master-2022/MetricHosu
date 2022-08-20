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
public class Alarm {

    private static final String ID_PREFIX = "metrichosu-alarm-";

    private Metric metric;

    @Builder.Default
    private int assessPeriod = 60;

    @Builder.Default
    private int evaluationPeriods = 1;

    private double threshold;

    private AlarmComparator comparator;

    public String getId() {
        return ID_PREFIX + metric.getId();
    }

    public String getMetricId() {
        return metric.getId();
    }
}
