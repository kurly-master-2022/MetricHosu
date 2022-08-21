package org.metrichosu.restapi.workflow.dto;

import lombok.Data;
import org.metrichosu.restapi.workflow.entity.*;

/**
 * @author : jbinchoo
 * @since : 2022-08-21
 */
@Data
public class WorkflowDefinitionDto {

    private String metricId;
    private String metricName;
    private String metricSourceUri;
    private AlarmDto alarm;
    private TriggerDto trigger;

    public WorkflowDefinitionDto(WorkflowDefinition definition, CollectionTrigger trigger) {
        Metric m = definition.getMetric();
        this.metricId = m.getId();
        this.metricName = m.getName();
        this.metricSourceUri = m.getMetricSourceUri();
        this.alarm = new AlarmDto(definition.getAlarm());
        this.trigger = (trigger != null)? new TriggerDto(trigger) : null;
    }
}


@Data
class AlarmDto {

    private final String alarmId;
    private final int alarmAssessPeriod;
    private final AlarmComparator alarmComparator;
    private final int alarmEvaludationPeriods;
    private final double alarmThreshold;

    public AlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.alarmAssessPeriod = alarm.getAssessPeriod();
        this.alarmComparator = alarm.getComparator();
        this.alarmEvaludationPeriods = alarm.getEvaluationPeriods();
        this.alarmThreshold = alarm.getThreshold();
    }
}

@Data
class TriggerDto {

    private final String ruleId;
    private final String targetId;
    private final String schedCron;
    private final boolean scheduled;
    private final boolean enabled;

    public TriggerDto(CollectionTrigger trigger) {
        this.ruleId = trigger.getRuleId();
        this.targetId = trigger.getTargetId();
        this.schedCron = trigger.getSchedCron();
        this.scheduled = trigger.isScheduled();
        this.enabled = trigger.isEnabled();
    }
}