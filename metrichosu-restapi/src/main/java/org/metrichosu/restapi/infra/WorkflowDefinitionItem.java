package org.metrichosu.restapi.infra;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrichosu.restapi.workflow.entity.*;

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

    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#metric_source_uri")
    private String metricSourceUri;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "#WorkflowDefinition#metric_type")
    private MetricType metricType;

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

    public static WorkflowDefinitionItem fromEntity(WorkflowDefinition definition) {
        var builder = WorkflowDefinitionItem.builder();
        builder.scheduled(definition.isScheduled());
        fromMetric(definition, builder);
        fromAlarm(definition, builder);
        fromTrigger(definition, builder);
        return builder.build();
    }

    private static void fromTrigger(WorkflowDefinition definition, WorkflowDefinitionItemBuilder builder) {
        CollectorTrigger t = definition.getTrigger();
        if (t != null) {
            builder.schedCron(t.getSchedCron());
        }
    }

    private static void fromAlarm(WorkflowDefinition definition, WorkflowDefinitionItemBuilder builder) {
        Alarm a = definition.getAlarm();
        if (a != null) {
            builder.alarmAssessPeriod(a.getAssessPeriod())
                    .alarmEvaluationPeriods(a.getEvaluationPeriods())
                    .alarmThreshold(a.getThreshold())
                    .alarmComparator(a.getComparator());
        }
    }

    private static void fromMetric(WorkflowDefinition definition, WorkflowDefinitionItemBuilder builder) {
        Metric m = definition.getMetric();
        if (m != null) {
            String mid = m.getId();
            builder.scheduled(definition.isScheduled())
                    .metricId(mid).pk(mid)
                    .metricName(m.getName())
                    .metricSourceUri(m.getSourceUri())
                    .metricType(m.getMetricType());
        }
    }

    public WorkflowDefinition toEntity() {
        Metric m = Metric.builder()
                .id(this.metricId)
                .name(this.metricName)
                .sourceUri(this.metricSourceUri)
                .metricType(this.metricType)
                .build();

        var builder = WorkflowDefinition.builder();
        builder.scheduled(this.scheduled);

        injectMetric(builder, m);
        injectAlarm(builder, m);
        injectTrigger(builder, m);
        return builder.build();
    }

    private void injectMetric(WorkflowDefinition.WorkflowDefinitionBuilder builder, Metric m) {
        builder.metric(m);
    }

    private void injectTrigger(WorkflowDefinition.WorkflowDefinitionBuilder builder, Metric m) {
        if (this.scheduled) {
            builder.trigger(CollectorTrigger.builder()
                    .metric(m)
                    .schedCron(this.schedCron)
                    .build());
        }
    }

    private void injectAlarm(WorkflowDefinition.WorkflowDefinitionBuilder builder, Metric m) {
        builder.alarm(Alarm.builder()
                .metric(m)
                .assessPeriod(this.alarmAssessPeriod)
                .evaluationPeriods(this.alarmEvaluationPeriods)
                .threshold(this.alarmThreshold)
                .comparator(this.alarmComparator).build());
    }
}
