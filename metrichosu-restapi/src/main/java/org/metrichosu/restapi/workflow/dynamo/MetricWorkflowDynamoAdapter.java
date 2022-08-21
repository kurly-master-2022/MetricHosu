package org.metrichosu.restapi.workflow.dynamo;

import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.infra.WorkflowDefinitionItem;
import org.metrichosu.restapi.infra.WorkflowDynamoRepository;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Component
@RequiredArgsConstructor
public class MetricWorkflowDynamoAdapter {

    private final WorkflowDynamoRepository repository;

    public WorkflowDefinition save(WorkflowDefinition definition) {
        return repository.save(WorkflowDefinitionItem.fromEntity(definition)).toEntity();
    }

    public WorkflowDefinition findByMetricId(String metricId) {
        return repository.findByPk(metricId).toEntity();
    }

    public List<WorkflowDefinition> findAll() {
        return repository.findAll().stream()
                .map(WorkflowDefinitionItem::toEntity)
                .collect(Collectors.toList());
    }

    public List<WorkflowDefinition> findAllByMetricId(String... metricIds) {
        return repository.findAllByPk(metricIds).stream()
                .map(WorkflowDefinitionItem::toEntity)
                .collect(Collectors.toList());
    }

    public void delete(WorkflowDefinition definition) {
        repository.delete(WorkflowDefinitionItem.fromEntity(definition));
    }

    public void deleteByMetricId(String metricId) {
        repository.deleteByPk(metricId);
    }

    public boolean existsByMetricId(String metricId) {
        return repository.existsByMetricId(metricId);
    }
}
