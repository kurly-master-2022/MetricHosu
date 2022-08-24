package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.exception.CloudClientRuntimeError;
import org.metrichosu.restapi.workflow.entity.FilterPolicy;

import java.util.List;
import java.util.Map;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@Slf4j
@RequiredArgsConstructor
public class SnsClient {

    private static final String ATTR_FILTER_POLICY = "FilterPolicy";

    private final AmazonSNS sns;
    private final String alarmMessageTopic;
    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, String> withFilter(FilterPolicy filterPolicy) throws JsonProcessingException {
        return Map.of(ATTR_FILTER_POLICY, mapper.writeValueAsString(filterPolicy));
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * AWS SNS에 구독을 등록합니다.
     * @param destination 구독 목적지
     * @param protocol 구독 프로토콜
     * @param targetMetrics 이 구독 목적지가 구독하는 메트릭
     */
    public void subscribe(String destination, String protocol, List<String> targetMetrics) {
        this.doSubscribe(destination, protocol, targetMetrics);
    }

    public void reSubscribe(String destination, String protocol, List<String> targetMetrics, String arn) {
        this.unSubscribe(arn);
        this.doSubscribe(destination, protocol, targetMetrics);
    }

    public void unSubscribe(String arnForDelete) {
        sns.unsubscribe(arnForDelete);
    }

    private void doSubscribe(String destination, String protocol, List<String> targetMetrics) {
        sns.subscribe(new SubscribeRequest()
                .withTopicArn(alarmMessageTopic)
                .withEndpoint(destination)
                .withProtocol(protocol)
                .withAttributes(withFilter(targetMetrics)));
    }

    private Map<String, String> withFilter(List<String> targetMetrics) {
        try {
            return Map.of(ATTR_FILTER_POLICY, new FilterPolicy(targetMetrics).asJson(mapper));
        } catch (Exception e) {
            throw new CloudClientRuntimeError("구독 필터 생성 중 실패했습니다.", e);
        }
    }
}
