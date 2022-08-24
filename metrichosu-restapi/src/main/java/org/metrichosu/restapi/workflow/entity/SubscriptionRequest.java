package org.metrichosu.restapi.workflow.entity;

import org.metrichosu.restapi.workflow.client.SnsClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
public class SubscriptionRequest {

    private List<String> metricIds;
    private String destination;
    private SnsProtocol protocol;

    public SubscriptionRequest(Subscription subscription) {
        this.metricIds = subscription.getMetricIds();
        this.destination = subscription.getDestination();
        this.protocol = subscription.getProtocol();
    }

    /**
     * 새 메트릭 구독 요청을 AWS로 전송합니다.
     * @param snsClient AWS SNS와 상호작용 가능한 클라이언트
     */
    public void send(SnsClient snsClient) {
        snsClient.subscribe(this.destination, this.protocol.value(), this.metricIds);
    }
}
