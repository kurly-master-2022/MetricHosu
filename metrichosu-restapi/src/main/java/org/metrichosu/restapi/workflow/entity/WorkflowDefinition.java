package org.metrichosu.restapi.workflow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.metrichosu.restapi.workflow.client.TriggerClient;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ToString
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class WorkflowDefinition {

    private final boolean scheduled;
    private final Metric metric;
    private final Alarm alarm;
    private final CollectorTrigger trigger;

    public WorkflowDefinition sync(TriggerClient triggerClient) {
        if (scheduled) {
            CollectorTrigger synched = trigger.sync(triggerClient);
            if(!trigger.equals(synched))
                return this.toBuilder().trigger(synched).build();
        }
        return this;
    }

    public boolean isSynchronized(TriggerClient triggerClient) {
        return this == this.sync(triggerClient);
    }

    public boolean isEnabled() {
        return trigger.isEnabled();
    }

    public WorkflowDefinition stateReversed() {
        return this.toBuilder().trigger(trigger.stateReversed()).build();
    }
}
