package org.metrichosu.notification.custom;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.metrichosu.notification.dto.input.CloudWatchAlarmMessage;

import java.util.Locale;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
public class SimpleAlarmMessage extends CustomCloudWatchAlarmMessage {

    private static final StringBuilder sb;
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER;

    static {
        sb = new StringBuilder();
        DEFAULT_DATETIME_FORMATTER = DateTimeFormat.forPattern("MM월dd일(E) HH:mm").
                withZone(DateTimeZone.forOffsetHours(9)).withLocale(new Locale("ko"));
    }

    private DateTimeFormatter dateTimeFormatter = DEFAULT_DATETIME_FORMATTER;

    /**
     * 커스텀 알람 메시지를 만듭니다.
     * @param original 원본 알람 메시지; CloudWatch가 SNS에게 발행한 형식.
     */
    public SimpleAlarmMessage(CloudWatchAlarmMessage original) {
        super(original);
    }

    @Override
    public String getVerbose() {
        sb.setLength(0);
        String title = String.format("알람 %s(%s)이 활성됨.\n", getAlarmName(), getAlarmArn());

        String transfer = String.format("%s의 %s/%s 메트릭이 %s=> %s로 전환되어 알람이 발생했습니다.%n",
                getRegion(), getMetricNamespace(), getMetricName(), getOldStateValue(), getNewStateValue());

        String condition = String.format("최근 %s회의 값이 역치 %s를 '%s' 형태로 위반하거나, 위반하지 않음. /  알람 설정 사유 - %s%n",
                getEvaluationPeriods(), getThreshold(), getComparisonOperator(), getNewStateReason());

        String time = String.format("발생 시간 - %s%n", dateTimeFormatter.print(getStateChangeTime()));
        return sb.append(title).append(transfer).append(condition).append(time).toString();
    }

    public void setDateTimeFormatter(DateTimeFormatter formatter) {
        this.dateTimeFormatter = formatter;
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }
}
