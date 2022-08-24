package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.model.*;
import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.workflow.entity.trigger.CollectorInput;
import org.metrichosu.restapi.workflow.entity.trigger.CollectorTrigger;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@RequiredArgsConstructor
public class TriggerClient {

    private final AmazonCloudWatchEvents events;
    private final String metricCollectorArn;

    public void putTrigger(CollectorTrigger trigger) {
        if (trigger != null) {
            this.putCollectorTriggerWithState(trigger, trigger.isEnabled());
            PutTargetsResult putTargetsResult = this.putTarget(trigger);
            assert putTargetsResult.getFailedEntryCount() == 0;
        }
    }

    public void putCollectorTriggerWithState(CollectorTrigger trigger, boolean state) {
            events.putRule(
                    new PutRuleRequest()
                            .withName(trigger.getRuleId())
                            .withState(state? RuleState.ENABLED : RuleState.DISABLED)
                            .withScheduleExpression(trigger.getSchedCron()));
    }

    private PutTargetsResult putTarget(CollectorTrigger trigger) {
        return events.putTargets(
                new PutTargetsRequest()
                        .withRule(trigger.getRuleId())
                        .withTargets(new Target()
                                .withId(trigger.getTargetId())
                                .withArn(metricCollectorArn)
                                .withInput(new CollectorInput(trigger.getMetric()).asJson()))
        );
    }

    public RuleState getRuleState(CollectorTrigger trigger) {
        return RuleState.valueOf(events.describeRule(
                new DescribeRuleRequest().withName(trigger.getRuleId())).getState());
    }

    public void delete(CollectorTrigger trigger) {
        events.removeTargets(new RemoveTargetsRequest()
                .withForce(true)
                .withRule(trigger.getRuleId())
                .withIds(trigger.getTargetId()));

        events.deleteRule(new DeleteRuleRequest()
                .withForce(true)
                .withName(trigger.getRuleId()));
    }
}
