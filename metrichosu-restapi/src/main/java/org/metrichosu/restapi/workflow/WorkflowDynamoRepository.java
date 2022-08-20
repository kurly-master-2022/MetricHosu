package org.metrichosu.restapi.workflow;

import org.springframework.data.repository.CrudRepository;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
public interface WorkflowDynamoRepository extends CrudRepository<WorkflowDefinitionItem, String> {
}
