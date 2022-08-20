package org.metrichosu.restapi.workflow.vo;

import lombok.Data;
import lombok.ToString;
import org.metrichosu.restapi.workflow.entity.*;
import org.springframework.boot.convert.DataSizeUnit;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
public class MetricMetadata {

    private String metricId;
    private String metricName;
    private int alarmAssessPeriod;
    private int alarmEvaluationPeriods;
    private double alarmThreshold;
    private AlarmComparator alarmComparator;
    private boolean scheduled;
    private String schedCron;

    public WorkflowDefinition getWorkflowDefinition() {
        Metric m = new Metric(metricId, metricName);
        return WorkflowDefinition.builder()
                .metric(new Metric(metricId, metricName))
                .alarm(Alarm.builder()
                        .metric(m)
                        .assessPeriod(alarmAssessPeriod)
                        .evaluationPeriods(alarmEvaluationPeriods)
                        .threshold(alarmThreshold)
                        .comparator(alarmComparator).build())
                .trigger(new CollectionTrigger(m, scheduled, schedCron))
                .build();
    }
}
