package org.metrichosu.notification.dto.output;

import lombok.Getter;
import lombok.ToString;
import org.metrichosu.notification.custom.CustomCloudWatchAlarmMessage;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
@ToString
@Getter
public class DeliveryMessage {

    private final String mid;
    private final String message;

    public DeliveryMessage(CustomCloudWatchAlarmMessage ccwam) {
        this.mid = ccwam.getMetricName();
        this.message = ccwam.getVerbose();
    }
}
