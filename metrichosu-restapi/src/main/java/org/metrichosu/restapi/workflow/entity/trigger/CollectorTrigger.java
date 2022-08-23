package org.metrichosu.restapi.workflow.entity.trigger;

import com.amazonaws.services.cloudwatchevents.model.RuleState;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.metrichosu.restapi.exception.CloudClientRuntimeError;
import org.metrichosu.restapi.workflow.entity.metric.Metric;

import java.util.Objects;

/**
 * {@code 트리거} 엔터티를 입니다. 메트릭 수집 트리거에 관한 설정을 보유합니다.
 * 또 AWS 클라우드 상의 Events Rule과 Target과 상호작용하여 데이터 일관성을 유지합니다.
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@ToString
@Getter
@Builder(toBuilder = true)
public class CollectorTrigger {

    private static final String RULE_ID_PREFIX = "metrichosu-rule-";
    private static final String TARGET_ID_PREFIX = "metrichosu-target-";

    private final Metric metric;
    private final String schedCron;
    private final boolean enabled;

    public CollectorTrigger(Metric metric, String schedCron, boolean enabled) {
        this.metric = metric;
        this.schedCron = this.wrapCron(Objects.requireNonNull(schedCron));
        this.enabled = enabled;
    }

    private String wrapCron(String cronExpression) {
        if (cronExpression.contains("(") && cronExpression.contains(")"))
            return cronExpression;
        return String.format("cron(%s)", cronExpression);
    }

    /**
     * CloudWatch Events Rule용 식별자를 획득합니다.
     * @return 단순히 "metrichosu-rule-{metric_id}" 입니다.
     */
    public String getRuleId() {
        return RULE_ID_PREFIX + metric.getId();
    }

    /**
     * CloudWatch Events Rule Target용 식별자를 획득합니다.
     * @return 단순히 "metrichosu-target-{metric_id}" 입니다.
     */
    public String getTargetId() {
        return TARGET_ID_PREFIX + metric.getId();
    }

    public String getPureCronExpression() {
        return schedCron.substring(5, schedCron.length() - 1);
    }

    /**
     * AWS상의 Events Rule 자원의 활성 상태를 조회합니다.
     * @param triggerClient AWS CloudWatch Events와 통신할 수 있는 클라이언트
     * @return {@code 트리거} 엔터티와 대응되는  CloudWatch Events Rule 자원의 활성 상태
     */
    public boolean getOriginState(TriggerClient triggerClient) throws CloudClientRuntimeError {
        try {
            RuleState s = triggerClient.getRuleState(this);
            return parseState(s);
        } catch (Exception e) {
            throwCloudClientException();
        }
        return false;
    }

    private boolean parseState(RuleState state) {
        return state.equals(RuleState.ENABLED);
    }

    private void throwCloudClientException() throws CloudClientRuntimeError {
        String message = String.format("트리거 %s의 AWS Events Rule 조회 중 오류 발생.");
        log.error(message);
        throw new CloudClientRuntimeError(message);
    }

    /**
     * AWS의 CloudWatch Events Rule의 상태를 기준으로 싱크합니다.
     * @param triggerClient
     * @return 싱크된 {@code 트리거} 엔터티. 싱크할 사항이 없으면 원래 객체를 반환.
     */
    public CollectorTrigger sync(TriggerClient triggerClient) {
        if (this.enabled != getOriginState(triggerClient)) {
            return this.stateReversed();
        }
        return this;
    }

    /**
     * @return 이 {@code 트리거} 엔터티의 활성 상태를 반전시킨 새 엔터티를 획득합니다.
     */
    public CollectorTrigger stateReversed() {
        return this.withState(!this.enabled);
    }

    /**
     * {@code 트리거} 엔터티의 활성 상태 값을 변경합니다. 변경이 반영된 새 객체가 생성됩니다.
     * @param  {@code true} 활성 상태로 변경할 때. {@code false} 비활성 상태로 변경할 때
     * @return 활성 상태 변경이 반영된 새 {@code 트리거} 엔터티
     */
    public CollectorTrigger withState(boolean enabled) {
        return this.toBuilder().enabled(enabled).build();
    }
}
