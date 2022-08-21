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
@Getter
@Builder
public class CollectionTrigger {

    private static final String RULE_ID_PREFIX = "metrichosu-rule-";
    private static final String TARGET_ID_PREFIX = "metrichosu-target-";

    private final Metric metric;
    private final boolean scheduled;
    private final String schedCron;
    private final boolean enabled;

    public CollectionTrigger(Metric metric, boolean scheduled, String schedCron, boolean enabled) {
        this.metric = metric;
        this.scheduled = scheduled;
        this.schedCron = scheduled? wrapCron(Objects.requireNonNull(schedCron)) : null;
        this.enabled = enabled;
    }

    private String wrapCron(String cronExpression) {
        if (cronExpression.contains("(") && cronExpression.contains(")"))
            return cronExpression;
        return String.format("cron(%s)", cronExpression);
    }

    public String getRuleId() {
        return RULE_ID_PREFIX + metric.getId();
    }

    public String getTargetId() {
        return TARGET_ID_PREFIX + metric.getId();
    }
}
