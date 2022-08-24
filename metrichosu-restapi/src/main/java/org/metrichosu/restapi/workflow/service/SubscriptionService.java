package org.metrichosu.restapi.workflow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.SnsClient;
import org.metrichosu.restapi.workflow.dynamo.SubscriptionDynamoAdapter;
import org.metrichosu.restapi.workflow.entity.Subscription;
import org.metrichosu.restapi.workflow.entity.SubscriptionRequest;
import org.metrichosu.restapi.workflow.entity.SubscriptionRow;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionDynamoAdapter dynamoAdapter;
    private final SnsClient snsClient;

    public List<SubscriptionRow> putSubscription(Subscription newSubscription) {
        String destination = newSubscription.getDestination();
        if (!dynamoAdapter.existsByDestination(destination)) {
            new SubscriptionRequest(newSubscription).send(snsClient);
            return dynamoAdapter.save(newSubscription);
        }
        return dynamoAdapter.findByDestination(destination);
    }

    private Set<SubscriptionRow> deleteSubscriberByName(String name, String arn) {
        Set<SubscriptionRow> deleted = new HashSet<>();
        if (name != null) {
            if (dynamoAdapter.existsByName(name)) {
                deleted.addAll(dynamoAdapter.findByName(name));
                dynamoAdapter.deleteAllByName(name);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 이름을 갖는 구독 수단이 영속되어 있지 않습니다.");
            }
            snsClient.unSubscribe(arn);
        }
        return deleted;
    }

    private Set<SubscriptionRow> deleteSubscriberByDestination(String destination, String arn) {
        Set<SubscriptionRow> deleted = new HashSet<>();
        if (destination != null) {
            if (dynamoAdapter.existsByDestination(destination)) {
                deleted.addAll(dynamoAdapter.findByDestination(destination));
                dynamoAdapter.deleteAllByDestination(destination);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 목적지를 갖는 구독 수단이 영속되어 있지 않습니다.");
            }
            snsClient.unSubscribe(arn);
        }
        return deleted;
    }

    public List<SubscriptionRow> deleteSubscriber(String name, String destination, String arn) {
        var setA = deleteSubscriberByName(name, arn);
        var setB = deleteSubscriberByDestination(destination, arn);
        setA.addAll(setB);

        if (setA.isEmpty())
            snsClient.unSubscribe(arn);
        return new ArrayList<>(setA);
    }

    public SubscriptionRow deleteById(String uuid) {
        var deleted = dynamoAdapter.findById(uuid)
                .orElseThrow(()-> new IllegalArgumentException("부정확한 uuid 입니다."));
        dynamoAdapter.deleteById(uuid);
        return deleted;
    }
}
