package org.metrichosu.restapi.workflow.service;

import com.amazonaws.services.cloudwatchevents.model.RuleState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.metrichosu.restapi.workflow.entity.CollectionTrigger;
import org.springframework.stereotype.Service;

/**
 * @author : jbinchoo
 * @since : 2022-08-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerClient triggerClient;

    public boolean getState(CollectionTrigger trigger) {
        try {
            RuleState s = triggerClient.getRuleState(trigger);
            return parseState(s);
        } catch (Exception e) {
            log.error("트리거 규칙 {} 조회 중 오류 발생.", trigger, e);
            return false;
        }
    }

    private boolean parseState(RuleState state) {
        return state.equals(RuleState.ENABLED);
    }

    public void updateState(CollectionTrigger trigger, boolean enabled) {
        validateScheduled(trigger);
        triggerClient.putCollectionTriggerWithState(trigger, enabled);
    }

    public CollectionTrigger exchange(CollectionTrigger trigger) {
        if (trigger == null) return null;

        return CollectionTrigger.builder()
                .metric(trigger.getMetric())
                .schedCron(trigger.getSchedCron())
                .scheduled(trigger.isScheduled())
                .enabled(trigger.isScheduled() && getState(trigger))
                .build();
    }

    private void validateScheduled(CollectionTrigger trigger) {
        if (!trigger.isScheduled())
            throw new IllegalStateException("스케줄 워크플로가 아니면, 트리거를 갖지 않습니다.");
    }
}
