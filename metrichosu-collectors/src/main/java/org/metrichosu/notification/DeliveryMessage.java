package org.metrichosu.notification;

import lombok.ToString;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
@ToString
public class DeliveryMessage {

    private final String mid;
    private final String message;

    public DeliveryMessage(CustomCloudWatchAlarmMessage ccwam) {
        this.mid = ccwam.getMetricName();
        this.message = ccwam.getVerbose();
    }
}
