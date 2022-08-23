package org.metrichosu.notification;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
public class AlarmMessageConverter {

    private static final String ALARM_MESSAGE_TOPIC = System.getenv("ALARM_MESSAGE_TOPIC");

    public void handle(SNSEvent snsEvent) {
        // get AlarmEvent for snsEvent, which is from AlarmTopic

        // extract metric-id, threshold, compartor, any other useful information

        // build a message string

        // publish the message to the AlarmMessageTopic
    }
}
