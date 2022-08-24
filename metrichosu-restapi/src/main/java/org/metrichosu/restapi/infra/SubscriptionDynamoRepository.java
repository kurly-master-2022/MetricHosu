package org.metrichosu.restapi.infra;

import org.metrichosu.restapi.workflow.entity.SnsProtocol;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@EnableScan
public interface SubscriptionDynamoRepository extends CrudRepository<SubscriptionItem, String> {

    Optional<SubscriptionItem> findByPk(String uuid);
    List<SubscriptionItem> findByName(String name);
    List<SubscriptionItem> findByDestination(String metricId);
    List<SubscriptionItem> findByMetricId(String metricId);
    List<SubscriptionItem> findByProtocol(SnsProtocol protocol);
    List<SubscriptionItem> findAll();

    void deleteByPk(String uuid);
    void deleteAllByName(String name);
    void deleteAllByDestination(String destination);
    void deleteAllByMetricId(String metricId);

    boolean existsByPk(String uuid);
    boolean existsByName(String name);
    boolean existsByMetricId(String metricId);
    boolean existsByDestination(String destination);
}
