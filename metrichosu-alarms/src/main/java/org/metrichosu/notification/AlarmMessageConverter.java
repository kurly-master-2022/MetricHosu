package org.metrichosu.notification;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.notification.custom.SimpleAlarmMessage;
import org.metrichosu.notification.dto.input.CloudWatchAlarmMessage;
import org.metrichosu.notification.dto.output.DeliveryMessage;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
@Slf4j
public class AlarmMessageConverter {

    private static final String ALARM_MESSAGE_TOPIC = System.getenv("ALARM_MESSAGE_TOPIC");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

    static {
        mapper.registerModule(new JodaModule());
    }

    public void handle(SNSEvent snsEvent) {
        this.deliver(convert(snsEvent));
    }

    private List<DeliveryMessage> convert(SNSEvent snsEvent) {
        return snsEvent.getRecords().stream()
                .map(this::getCloudWatchAlarmMessage)
                .filter(Objects::nonNull)
                .map(SimpleAlarmMessage::new)
                .map(DeliveryMessage::new)
                .collect(Collectors.toList());
    }

    private CloudWatchAlarmMessage getCloudWatchAlarmMessage(SNSEvent.SNSRecord snsRecord) {
        String m = snsRecord.getSNS().getMessage();
        try {
            return mapper.readValue(m, CloudWatchAlarmMessage.class);
        } catch (JsonProcessingException e) {
            log.error("알람 메시지 추출 중 오류 발생: {}", m);
            return null;
        }
    }

    private void deliver(List<DeliveryMessage> deliveries) {
        for (DeliveryMessage m : deliveries) {
            log.info(m.toString());
            try {
                snsClient.publish(ALARM_MESSAGE_TOPIC, mapper.writeValueAsString(m));
            } catch (JsonProcessingException e) {
                log.error("전달 메시지 발행 중 오류 발생: {}", m);
            }
        }
    }
}
