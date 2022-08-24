package org.metrichosu.restapi.workflow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@ToString
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Subscription {

    private final String uuid; // pk
    private final String name;
    private final List<String> metricIds;
    private final SnsProtocol protocol;
    private final String destination;

    public Set<SubscriptionRow> asRows() {
        Set<SubscriptionRow> rows = new HashSet<>();
        for (String metricId : this.getMetricIds()) {
            rows.add(new SubscriptionRow(this, metricId));
        }
        return rows;
    }
}
