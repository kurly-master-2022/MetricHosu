package org.metrichosu.notification.custom;

import org.joda.time.DateTime;
import org.metrichosu.notification.dto.input.CloudWatchAlarmMessage;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
public abstract class CustomCloudWatchAlarmMessage {

    protected CloudWatchAlarmMessage original;

    /**
     * 커스텀 알람 메시지를 만듭니다.
     * @param original 원본 알람 메시지; CloudWatch가 SNS에게 발행한 형식.
     */
    public CustomCloudWatchAlarmMessage(CloudWatchAlarmMessage original) {
        this.original = original;
    }

    /**
     * 커스텀 알람 메시지 문자열을 구성하여 반환합니다.
     * @return 커스텀 알람 메시지 문자열
     */
    public abstract String getVerbose();

    public String getAlarmName() {
        return original.getAlarmName();
    }

    public String getNewStateValue() {
        return original.getNewStateValue();
    }

    public String getOldStateValue() {
        return original.getOldStateValue();
    }

    public String getNewStateReason() {
        return original.getNewStateReason();
    }

    public DateTime getStateChangeTime() {
        return original.getStateChangeTime();
    }

    public String getAlarmArn() {
        return original.getAlarmArn();
    }

    public String getRegion() {
        return original.getRegion();
    }

    public String getMetricName() {
        return original.getTrigger().getMetricName();
    }

    public String getMetricNamespace() {
        return original.getTrigger().getNamespace();
    }

    public String getStatistic() {
        return original.getTrigger().getStatistic();
    }

    public int getEvaluationPeriods() {
        return original.getTrigger().getEvaluationPeriods();
    }

    public String getComparisonOperator() {
        return original.getTrigger().getComparisonOperator();
    }

    public double getThreshold() {
        return original.getTrigger().getThreshold();
    }
}
