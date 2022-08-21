package org.metrichosu.restapi.infra;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrichosu.restapi.workflow.entity.*;
import org.springframework.scheduling.Trigger;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
@Builder
@DynamoDBTable(tableName = "metrichosu")
@NoArgsConstructor // required
@AllArgsConstructor
public class WorkflowDefinitionItem {

    @DynamoDBHashKey
    private String pk; // metricId

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#metric_id")
    private String metricId;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#metric_name")
    private String metricName;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#alarm_assess_period")
    private int alarmAssessPeriod;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#alarm_evaluation_periods")
    private int alarmEvaluationPeriods;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#alarm_threshold")
    private double alarmThreshold;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#alarm_comparator")
    private AlarmComparator alarmComparator;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#scheduled")
    private boolean scheduled;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#sched_cron")
    private String schedCron;

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#metric_uri")
    private String metricUri;

    public static WorkflowDefinitionItem fromEntity(WorkflowDefinition definition) {
        Metric m = definition.getMetric();
        String mid = m.getId(), mname = m.getName();

        Alarm a = definition.getAlarm();
        CollectionTrigger t = definition.getTrigger();

        return WorkflowDefinitionItem.builder()
                .metricId(mid).pk(mid)
                .metricName(mname)
                .alarmAssessPeriod(a.getAssessPeriod())
                .alarmEvaluationPeriods(a.getEvaluationPeriods())
                .alarmThreshold(a.getThreshold())
                .alarmComparator(a.getComparator())
                .scheduled(t.isScheduled())
                .schedCron(t.getSchedCron())
                .build();
    }

    public WorkflowDefinition toEntity() {
        Metric m = new Metric(metricId, metricName, metricUri);
        return WorkflowDefinition.builder()
                .metric(m)
                .alarm(new Alarm(m, alarmAssessPeriod, alarmEvaluationPeriods, alarmThreshold, alarmComparator))
                .trigger(new CollectionTrigger(m, scheduled, schedCron, false))
                .build();
    }
}
