package org.metrichosu.restapi.workflow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ToString
@Builder
@Getter
public class CollectionTrigger {

    private static final String RULE_ID_PREFIX = "metrichosu-rule-";
    private static final String TARGET_ID_PREFIX = "metrichosu-target-";

    private static final String DUMMY_SCHED_CRON = "0 0 1 * ? 2020";

    private final Metric metric;

    private final boolean scheduled;

    private final String schedCron;

    public CollectionTrigger(Metric metric, boolean scheduled, String schedCron) {
        this.metric = metric;
        this.scheduled = scheduled;
        this.schedCron = scheduled? wrapCron(Objects.requireNonNull(schedCron)) : schedCron;
    }

    private String wrapCron(String cronExpression) {
        return String.format("cron(%s)", cronExpression);
    }

    public String getSchedCron() {
        return scheduled? schedCron : DUMMY_SCHED_CRON;
    }

    public String getRuleId() {
        return RULE_ID_PREFIX + metric.getId();
    }

    public String getTargetId() {
        return TARGET_ID_PREFIX + metric.getId();
    }

    public String getMetricId() {
        return metric.getId();
    }
}
