package org.metrichosu.restapi.workflow.entity;

import lombok.*;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ToString
@Getter
@Builder
@RequiredArgsConstructor
public class Alarm {

    private static final String ID_PREFIX = "metrichosu-alarm-";

    private final Metric metric;

    @Builder.Default
    private final int assessPeriod = 60;

    @Builder.Default
    private final int evaluationPeriods = 1;

    private final double threshold;

    private final AlarmComparator comparator;

    public String getId() {
        return ID_PREFIX + metric.getId();
    }

    public String getMetricId() {
        return metric.getId();
    }
}
