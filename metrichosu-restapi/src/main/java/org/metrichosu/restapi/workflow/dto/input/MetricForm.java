package org.metrichosu.restapi.workflow.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.metrichosu.restapi.workflow.entity.*;
import org.metrichosu.restapi.workflow.entity.alarm.Alarm;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmComparator;
import org.metrichosu.restapi.workflow.entity.metric.Metric;
import org.metrichosu.restapi.workflow.entity.metric.MetricType;
import org.metrichosu.restapi.workflow.entity.trigger.CollectorTrigger;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * 메트릭 수집 워크플로를 정의할 수 있는 입력 폼을 표상합니다.
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
@NoArgsConstructor
public class MetricForm {

    @NotNull
    private String metricId;

    private String metricName = "";
    private String metricSourceUri;

    @NotNull
    private MetricType metricType;

    private int alarmAssessPeriod = 60;
    private int alarmEvaluationPeriods = 1;

    @NotNull
    private Double alarmThreshold;

    @NotNull
    private AlarmComparator alarmComparator;

    private boolean scheduled;
    private String schedCron;
    private boolean enabled = false;

    @AssertTrue(message = "스케줄 워크플로는 크론식과 메트릭 소스 URI가 필요합니다.")
    public boolean isValidScheduledWorkflow() {
        return !scheduled || (schedCron != null && metricSourceUri != null);
    }

    /**
     * 이 입력 폼을 토대로 {@code 워크플로 정의} 엔터티를 구성합니다.
     * @return 워크플로 정의
     */
    public WorkflowDefinition toWorkflowDefinition() {
        Metric m = this.toMetric();
        Alarm a = this.toAlarm(m);
        CollectorTrigger t = this.toTrigger(m);
        return WorkflowDefinition.builder()
                .scheduled(this.scheduled)
                .metric(m).alarm(a).trigger(t)
                .build();
    }

    /**
     * 메트릭 엔터티를 만들어 반환합니다.
     * @return 메트릭
     */
    private Metric toMetric() {
        return Metric.builder()
                .id(metricId)
                .name(metricName)
                .sourceUri(metricSourceUri)
                .metricType(metricType)
                .build();
    }

    /**
     * 알람 엔터티를 만들어 반환합니다.
     * @param m 메트릭
     * @return 알람
     */
    private Alarm toAlarm(Metric m) {
        return Alarm.builder()
                .metric(m)
                .assessPeriod(alarmAssessPeriod)
                .evaluationPeriods(alarmEvaluationPeriods)
                .threshold(alarmThreshold)
                .comparator(alarmComparator)
                .build();
    }

    /**
     * 트리거 엔터티를 만들어 반환합니다.
     * @param m 메트릭
     * @return {@code null} 비동기 워크플로일 경우
     */
    private CollectorTrigger toTrigger(Metric m) {
        return (this.scheduled)? CollectorTrigger.builder()
                .metric(m)
                .enabled(this.enabled)
                .schedCron(schedCron)
                .build() : null;
    }
}
