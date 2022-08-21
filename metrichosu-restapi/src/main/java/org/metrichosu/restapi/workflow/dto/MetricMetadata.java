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
                .trigger(new CollectionTrigger(m, scheduled, schedCron))
                .build();
    }

    public MetricMetadata(WorkflowDefinition definition) {
        Metric m = definition.getMetric();
        Alarm a = definition.getAlarm();
        CollectionTrigger t = definition.getTrigger();
        this.metricId = m.getId();
        this.metricName = m.getName();
        this.alarmAssessPeriod = a.getAssessPeriod();
        this.alarmEvaluationPeriods = a.getEvaluationPeriods();
        this.alarmComparator = a.getComparator();
        this.alarmThreshold = a.getThreshold();
        this.scheduled = t.isScheduled();
        this.schedCron = t.getSchedCron();
        this.metricSourceUri = m.getMetricSourceUri();
    }
}
