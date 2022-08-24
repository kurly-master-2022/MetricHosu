package org.metrichosu.restapi.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SubscriptionRow {

    private final String uuid;
    private final String name;
    private final String metricId;
    private final SnsProtocol protocol;
    private final String destination;

    public SubscriptionRow(Subscription subscription, String metricId) {
        this.uuid = subscription.getUuid();
        this.name = subscription.getName();
        this.metricId = metricId;
        this.protocol = subscription.getProtocol();
        this.destination = subscription.getDestination();
    }
}