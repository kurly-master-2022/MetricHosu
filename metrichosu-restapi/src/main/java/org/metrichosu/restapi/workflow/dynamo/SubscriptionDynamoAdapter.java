package org.metrichosu.restapi.workflow.dynamo;

import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.infra.SubscriptionDynamoRepository;
import org.metrichosu.restapi.infra.SubscriptionItem;
import org.metrichosu.restapi.workflow.entity.SnsProtocol;
import org.metrichosu.restapi.workflow.entity.Subscription;
import org.metrichosu.restapi.workflow.entity.SubscriptionRow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@Component
@RequiredArgsConstructor
public class SubscriptionDynamoAdapter {

    private final SubscriptionDynamoRepository repository;

    public Optional<SubscriptionRow> findById(String uuid) {
        return repository.findByPk(uuid).map(SubscriptionItem::toEntity);
    }

    public List<SubscriptionRow> findByName(String name) {
        return repository.findByDestination(name).stream()
                .map(SubscriptionItem::toEntity).collect(Collectors.toList());
    }

    public List<SubscriptionRow> findByDestination(String destination) {
        return repository.findByDestination(destination).stream()
                .map(SubscriptionItem::toEntity).collect(Collectors.toList());
    }

    public List<SubscriptionRow> findByMetricId(String metricId) {
        return repository.findByMetricId(metricId).stream()
                .map(SubscriptionItem::toEntity).collect(Collectors.toList());
    }

    public List<SubscriptionRow> findByProtocol(SnsProtocol protocol) {
        return repository.findByProtocol(protocol).stream()
                .map(SubscriptionItem::toEntity).collect(Collectors.toList());
    }

    public List<SubscriptionRow> findAll() {
        return repository.findAll().stream()
                .map(SubscriptionItem::toEntity).collect(Collectors.toList());
    }

    public void deleteById(String uuid) {
        repository.deleteByPk(uuid);
    }

    public void deleteAllByName(String name) {
        repository.deleteAllByName(name);
    }

    public void deleteAllByDestination(String destination) {
        repository.deleteAllByDestination(destination);
    }

    public void deleteAllByMetricId(String metricId) {
        repository.deleteAllByMetricId(metricId);
    }

    public boolean existsById(String uuid) {
        return repository.existsByPk(uuid);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public boolean existsByMetricId(String metricId) {
        return repository.existsByMetricId(metricId);
    }

    public boolean existsByDestination(String destination) {
        return repository.existsByDestination(destination);
    }

    public List<SubscriptionRow> save(Subscription entity) {
        return entity.asRows().stream()
                .map(row-> repository.save(SubscriptionItem.fromEntity(row)))
                .map(SubscriptionItem::toEntity)
                .collect(Collectors.toList());
    }
}
