package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.model.*;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.entity.CollectionTrigger;
import org.metrichosu.restapi.workflow.entity.MetricInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@Component
public class TriggerClient {

    @Autowired
    @Qualifier("metric-collector-arn")
    private String metricCollectorArn;

    private final AmazonCloudWatchEvents events;

    public TriggerClient(AmazonCloudWatchEvents events) {
        this.events = events;
    }

    public void putCollectionTrigger(CollectionTrigger trigger) {
        if (trigger.isScheduled()) {
            this.putCollectionTriggerWithState(trigger, trigger.isEnabled());
            PutTargetsResult putTargetsResult = this.putTarget(trigger);
            assert putTargetsResult.getFailedEntryCount() == 0;
        }
    }

    public void putCollectionTriggerWithState(CollectionTrigger trigger, boolean state) {
            events.putRule(
                    new PutRuleRequest()
                            .withName(trigger.getRuleId())
                            .withState(state? RuleState.ENABLED : RuleState.DISABLED)
                            .withScheduleExpression(trigger.getSchedCron()));
    }

    private PutTargetsResult putTarget(CollectionTrigger trigger) {
        return events.putTargets(
                new PutTargetsRequest()
                        .withRule(trigger.getRuleId())
                        .withTargets(new Target()
                                .withId(trigger.getTargetId())
                                .withArn(metricCollectorArn)
                                .withInput(new MetricInput(trigger.getMetric()).asJson()))
        );
    }

    public RuleState getRuleState(CollectionTrigger trigger) {
        return RuleState.valueOf(events.describeRule(
                new DescribeRuleRequest().withName(trigger.getRuleId())).getState());
    }

    public void setMetricCollectorArn(String metricCollectorArn) {
        this.metricCollectorArn = metricCollectorArn;
    }

    public void delete(CollectionTrigger trigger) {
        events.removeTargets(new RemoveTargetsRequest()
                .withForce(true)
                .withRule(trigger.getRuleId())
                .withIds(trigger.getTargetId()));

        events.deleteRule(new DeleteRuleRequest()
                .withForce(true)
                .withName(trigger.getRuleId()));
    }
}
