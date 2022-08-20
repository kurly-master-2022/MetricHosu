package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClient;
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

    public void register(CollectionTrigger trigger) {
        registerRule(trigger);
        var putTargetsResult = registerTarget(trigger);
        assert putTargetsResult.getFailedEntryCount() == 0;
    }

    private PutRuleResult registerRule(CollectionTrigger trigger) {
        log.info(trigger.toString());
        return events.putRule(
                new PutRuleRequest()
                        .withName(trigger.getRuleId())
                        .withState(trigger.isScheduled()? RuleState.ENABLED : RuleState.DISABLED)
                        .withScheduleExpression(trigger.getSchedCron()));
    }

    private PutTargetsResult registerTarget(CollectionTrigger trigger) {
        return events.putTargets(
                new PutTargetsRequest()
                        .withRule(trigger.getRuleId())
                        .withTargets(new Target()
                                .withId(trigger.getTargetId())
                                .withArn(metricCollectorArn)
                                .withInput(new MetricInput(trigger.getMetric()).asJson()))
        );
    }

    public void setMetricCollectorArn(String metricCollectorArn) {
        this.metricCollectorArn = metricCollectorArn;
    }
}
