package org.metrichosu.restapi.workflow.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrichosu.restapi.workflow.entity.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
@NoArgsConstructor
public class MetricMetadata {

    @NotNull
    private String metricId;
    private String metricName = "";
    private int alarmAssessPeriod = 60;
    private int alarmEvaluationPeriods = 1;

    @NotNull
    private double alarmThreshold;

    @NotNull
    private AlarmComparator alarmComparator;

    private boolean scheduled;
    private boolean enabled = false;
    private String schedCron;
    private String metricSourceUri;

    @AssertTrue(message = "scheduled=true이면 schedCron와 metricSourceUri가 필요합니다.")
    public boolean hasValidSchedule() {
        return !scheduled || (schedCron != null && metricSourceUri != null);
    }

    public WorkflowDefinition toEntity() {
        Metric m = new Metric(metricId, metricName, metricSourceUri);
        return WorkflowDefinition.builder()
                .metric(m)
                .alarm(Alarm.builder()
                        .metric(m)
                        .assessPeriod(alarmAssessPeriod)
                        .evaluationPeriods(alarmEvaluationPeriods)
                        .threshold(alarmThreshold)
                        .comparator(alarmComparator).build())
                .trigger(new CollectionTrigger(m, scheduled, schedCron, enabled))
                .build();
    }
}
