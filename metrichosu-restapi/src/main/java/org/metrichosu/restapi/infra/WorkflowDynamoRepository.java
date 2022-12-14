package org.metrichosu.restapi.infra;

import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@EnableScan
public interface WorkflowDynamoRepository extends CrudRepository<WorkflowDefinitionItem, String> {

    Optional<WorkflowDefinitionItem> findByPk(String metricId);

    List<WorkflowDefinitionItem> findAll();

    List<WorkflowDefinitionItem> findAllByPk(String... metricIds);

    void deleteByPk(String metricId);

    boolean existsByMetricId(String metricId);
}
