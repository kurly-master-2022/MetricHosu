package org.metrichosu.restapi.workflow.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.AlarmClient;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.metrichosu.restapi.workflow.entity.Metric;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.metrichosu.restapi.workflow.infra.MetricWorkflowDynamoAdapter;
import org.springframework.stereotype.Service;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WorkflowService {

    //TODO : make this final
    private MetricWorkflowDynamoAdapter dynamoAdapter;
    private final AlarmClient alarmClient;
    private final TriggerClient triggerClient;

    public boolean createWorkflow(WorkflowDefinition definition) {
        if (this.contains(definition))
            return false;
        try {
            alarmClient.register(definition.getAlarm());
            triggerClient.register(definition.getTrigger());
        } catch (Exception e) {
            log.error(definition.toString());
            log.error("Failed to create a metric workflow.", e);
            return false;
        }
        return true;
    }

    public boolean contains(WorkflowDefinition workflow) {
        return this.contains(workflow.getMetric());
    }

    public boolean contains(Metric metric) {
        return this.contains(metric.getId());
    }

    public boolean contains(String metricId) {
        return false;
    }
}

