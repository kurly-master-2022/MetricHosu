package org.metrichosu.restapi.workflow.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.exception.CloudClientRuntimeError;
import org.metrichosu.restapi.exception.MetricNotFoundException;
import org.metrichosu.restapi.exception.WorkflowTypeMismatched;
import org.metrichosu.restapi.workflow.client.AlarmClient;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.metrichosu.restapi.workflow.dynamo.MetricWorkflowDynamoAdapter;
import org.metrichosu.restapi.workflow.entity.Alarm;
import org.metrichosu.restapi.workflow.entity.Metric;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.springframework.stereotype.Service;

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

    /**
     * {@code 워크플로} 엔터티를 영속할 뿐만 아니라, 대응하는 AWS 상의 워크플로 자원을 생성합니다.
     * @param definition 영속할 {@code 워크플로} 엔터티
     * @return 영속 및 AWS 자원 생성에 성공한 {@code 워크플로} 엔터티
     */
    public WorkflowDefinition putWorkflow(WorkflowDefinition definition) throws CloudClientRuntimeError {
        String metricId = definition.getMetric().getId();
        WorkflowDefinition previousDefinition = this.contains(definition)? this.describeOriginWorkflow(metricId) : null;
        return this.handlePutWorkflowResult(definition, atomicPutEntity(definition, metricId, previousDefinition));
    }

    private String atomicPutEntity(WorkflowDefinition definition, String metricId, WorkflowDefinition previous) {

        String exMessage;
        try {
            WorkflowDefinition savedEntity = this.dynamoAdapter.save(definition);
            exMessage = atomicPutAlarm(definition, metricId, savedEntity, previous);
        } catch (Exception e) {
            exMessage = String.format("%s 엔터티 영속 중 오류가 발생했습니다.", metricId);
            log.error(exMessage, e);
        }
        return exMessage;
    }

    private String atomicPutAlarm(WorkflowDefinition definition, String metricId,
                                  WorkflowDefinition savedEntity, WorkflowDefinition previous) {

        String exMessage;
        try {
            Alarm alarmToCreate = definition.getAlarm();
            alarmClient.register(definition.getAlarm());
            exMessage = atomicPutTrigger(definition, metricId, savedEntity, alarmToCreate, previous);
        } catch (Exception e) {
            exMessage = String.format("%s의 알람 생성 중 오류가 발생했습니다.", metricId);
            rollbackDynamoEntity(savedEntity, previous);
            log.error(exMessage, e);
        }
        return exMessage;
    }

    private void rollbackDynamoEntity(WorkflowDefinition savedEntity, WorkflowDefinition previous) {
        if (previous == null) {
            dynamoAdapter.delete(savedEntity); // rollback dynamodb entity
            log.warn("엔터티 롤백 전략 - 삭제");
        }
        else {
            dynamoAdapter.save(previous);
            log.warn("엔터티 롤백 전략 - 되돌리기");
        }
    }

    private String atomicPutTrigger(WorkflowDefinition definition, String metricId,
                                    WorkflowDefinition savedEntity, Alarm createdAlarm, WorkflowDefinition previous) {

        String exMessage = null;
        try {
            triggerClient.putTrigger(definition.getTrigger());
        } catch (Exception e) {
            exMessage = String.format("%s의 트리거 생성 중 오류가 발생했습니다.", metricId);
            rollbackAlarm(createdAlarm, previous);
            rollbackDynamoEntity(savedEntity, previous);
            log.error(exMessage, e);
        }
        return exMessage;
    }

    private void rollbackAlarm(Alarm createdAlarm, WorkflowDefinition previous) {
        if (previous == null) {
            alarmClient.delete(createdAlarm); // rollback cloudwatch alarm
            log.warn("알람 롤백 전략 - 삭제");
        }
        else {
            alarmClient.register(previous.getAlarm());
            log.warn("알람 롤백 전략 - 되돌리기");
        }
    }

    private WorkflowDefinition handlePutWorkflowResult(WorkflowDefinition definition, String exMessage)
            throws CloudClientRuntimeError {

        if (exMessage != null) {
            RuntimeException ex = new CloudClientRuntimeError(exMessage);
            log.error("워크플로 생성 중 오류 때문에 롤백이 발생했습니다.", ex);
            throw ex;
        }
        return definition;
    }

    /**
     * 영속된 {@code 워크플로} 엔터티를 반환하되
     * 실제 AWS 상의 워크플로 자원들의 상태를 조회하고, 그들 기준으로 싱크한 뒤 반환합니다.
     * @param metricId 워크플로의 메트릭의 아이디
     * @return 워크플로 엔터티
     */
    public WorkflowDefinition describeOriginWorkflow(String metricId) throws CloudClientRuntimeError {
        WorkflowDefinition definition = this.queryWorkflow(metricId);
        return definition.sync(this.triggerClient);
    }

    /**
     * 영속된 {@code 워크플로} 엔터티를 반환합니다.
     * @param metricId 워크플로의 메트릭의 아이디
     * @return 워크플로 엔터티
     */
    private WorkflowDefinition queryWorkflow(String metricId) throws MetricNotFoundException {
        return this.dynamoAdapter.findByMetricId(metricId)
                .orElseThrow(MetricNotFoundException::new);
    }

    /**
     * {@code 워크플로} 엔터티의 활성 상태를 주어진 상태로 변경합니다.
     * 이 변경사항은 영속되며 AWS에도 반영됩니다. 반열을 마치면 변경된 상태의 엔터티를 반환합니다.
     * @param metricId 메트릭 아이디
     * @param enable 활성 상태
     * @return 상태 변경 완료 후의 {@code 워크플로} 엔터티
     */
    public WorkflowDefinition putWorkflowState(String metricId, boolean enable) throws WorkflowTypeMismatched {
        WorkflowDefinition definition = this.describeOriginWorkflow(metricId);
        boolean isScheduled = definition.isScheduled();
        if (isScheduled) {
            boolean needUpdate = definition.isEnabled() != enable;
            if (needUpdate) {
                definition = definition.stateReversed();
                this.triggerClient.putTrigger(definition.getTrigger());
                this.dynamoAdapter.save(definition);
            }
            return definition;
        }
        else {
            throw new WorkflowTypeMismatched();
        }
    }

    // TODO: 트랜잭션을 완벽하게 설계하라.
    public void deleteOriginWorkflow(String metricId) throws MetricNotFoundException {
        if (this.dynamoAdapter.existsByMetricId(metricId)) {
            WorkflowDefinition definition = this.queryWorkflow(metricId);
            this.dynamoAdapter.deleteByMetricId(metricId);
            this.alarmClient.delete(definition.getAlarm());
            this.triggerClient.delete(definition.getTrigger());
        } else {
            throw new MetricNotFoundException();
        }
    }

    public boolean contains(WorkflowDefinition workflow) {
        return this.contains(workflow.getMetric());
    }

    public boolean contains(Metric metric) {
        return this.contains(metric.getId());
    }

    public boolean contains(String metricId) {
        return this.dynamoAdapter.existsByMetricId(metricId);
    }
}
