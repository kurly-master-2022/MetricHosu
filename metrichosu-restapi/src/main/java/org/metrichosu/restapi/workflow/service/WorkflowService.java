package org.metrichosu.restapi.workflow.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.AlarmClient;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.metrichosu.restapi.workflow.dynamo.MetricWorkflowDynamoAdapter;
import org.metrichosu.restapi.workflow.entity.Metric;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WorkflowService {

    private final MetricWorkflowDynamoAdapter dynamoAdapter;
    private final AlarmClient alarmClient;
    private final TriggerClient triggerClient;

    // TODO: 트랜잭션을 완벽하게 설계하라.
    public WorkflowDefinition create(WorkflowDefinition definition) {
        try {
            dynamoAdapter.save(definition);
            alarmClient.register(definition.getAlarm());
            triggerClient.putCollectionTrigger(definition.getTrigger());
        } catch (Exception e) {
            log.error(definition.toString());
            log.error("Failed to create a metric workflow.", e);
            return null;
        }
        return definition;
    }

    // TODO: 트랜잭션을 완벽하게 설계하라.
    public void delete(String metricId) {
        if (dynamoAdapter.existsByMetricId(metricId)) {
            WorkflowDefinition definition = this.find(metricId);
            dynamoAdapter.deleteByMetricId(metricId);
            alarmClient.delete(definition.getAlarm());
            triggerClient.delete(definition.getTrigger());
        } else {
            throw this.createNotFoundExecption();
        }
    }

    public WorkflowDefinition find(String metricId) {
        return dynamoAdapter.findByMetricId(metricId)
                .orElseThrow(this::createNotFoundExecption);
    }

    public boolean contains(WorkflowDefinition workflow) {
        return this.contains(workflow.getMetric());
    }

    public boolean contains(Metric metric) {
        return this.contains(metric.getId());
    }

    public boolean contains(String metricId) {
        return dynamoAdapter.existsByMetricId(metricId);
    }

    private ResponseStatusException createNotFoundExecption() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "등록되지 않은 메트릭입니다.");
    }
}

