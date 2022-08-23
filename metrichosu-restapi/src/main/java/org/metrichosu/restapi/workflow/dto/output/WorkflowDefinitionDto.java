package org.metrichosu.restapi.workflow.dto.output;

import lombok.Data;
import org.metrichosu.restapi.workflow.entity.*;
import org.metrichosu.restapi.workflow.entity.alarm.Alarm;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmComparator;
import org.metrichosu.restapi.workflow.entity.metric.Metric;
import org.metrichosu.restapi.workflow.entity.metric.MetricType;
import org.metrichosu.restapi.workflow.entity.trigger.CollectorTrigger;

/**
 * {@code 워크플로 정의} 엔터티의 출력용 형식 데이터입니다.
 * @author : jbinchoo
 * @since : 2022-08-21
 */
@Data
public class WorkflowDefinitionDto {

    private Boolean scheduled;
    private String metricId;
    private String metricName;
    private String metricSourceUri;
    private MetricType metricType;
    private AlarmDto alarm;
    private TriggerDto trigger;

    /**
     * {@code 워크플로 정의} 엔터티의 출력 형식 데이터를 구성합니다.
     * @param definition
     */
    public WorkflowDefinitionDto(WorkflowDefinition definition) {
        this.scheduled = definition.isScheduled();
        this.setupMetric(definition.getMetric());
        this.setupAlarm(definition.getAlarm());
        this.setupTrigger(definition.getTrigger());
    }

    private void setupMetric(Metric m) {
        this.metricId = m.getId();
        this.metricName = m.getName();
        this.metricSourceUri = m.getSourceUri();
        this.metricType = m.getMetricType();
    }

    private void setupAlarm(Alarm alarm) {
        this.alarm = new AlarmDto(alarm);
    }

    /**
     * @param trigger 트리거의 출력 형식 데이터. {@code null} 비동기 워크플로일 경우
     */
    private void setupTrigger(CollectorTrigger trigger) {
        this.trigger = (trigger != null)? new TriggerDto(trigger) : null;
    }
}

/**
 * {@code 알람} 엔터티의 출력 형식 데이터입니다.
 */
@Data
class AlarmDto {

    private final String alarmId;
    private final Integer alarmAssessPeriod;
    private final AlarmComparator alarmComparator;
    private final Integer alarmEvaludationPeriods;
    private final Double alarmThreshold;

    public AlarmDto(Alarm alarm) {
        this.alarmId = alarm.getId();
        this.alarmAssessPeriod = alarm.getAssessPeriod();
        this.alarmComparator = alarm.getComparator();
        this.alarmEvaludationPeriods = alarm.getEvaluationPeriods();
        this.alarmThreshold = alarm.getThreshold();
    }
}

/**
 * {@code 트리거} 엔터티의 출력 형식 데이터입니다.
 */
@Data
class TriggerDto {

    private final String ruleId;
    private final String targetId;
    private final String schedCron;
    private final Boolean enabled;

    public TriggerDto(CollectorTrigger trigger) {
        this.ruleId = trigger.getRuleId();
        this.targetId = trigger.getTargetId();
        this.schedCron = trigger.getPureCronExpression();
        this.enabled = trigger.isEnabled();
    }
}
