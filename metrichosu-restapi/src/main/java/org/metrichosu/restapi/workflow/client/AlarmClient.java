package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.*;
import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.workflow.entity.alarm.Alarm;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmStateValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
public class AlarmClient {

    private final AmazonCloudWatch cloudWatch;
    private String alarmTopicArn;
    private String alarmMessageTopicArn;

    public AlarmClient(AmazonCloudWatch cloudWatch, String alarmTopicArn, String alarmMessageTopicArn) {
        this.cloudWatch = cloudWatch;
        this.alarmTopicArn = alarmTopicArn;
        this.alarmMessageTopicArn = alarmMessageTopicArn;
    }

    public void register(Alarm alarm) {
        cloudWatch.putMetricAlarm(
                new PutMetricAlarmRequest()
                        .withNamespace("metrichosu")
                        .withMetricName(alarm.getMetricId())
                        .withAlarmName(alarm.getId())
                        .withPeriod(alarm.getAssessPeriod())
                        .withEvaluationPeriods(alarm.getEvaluationPeriods())
                        .withStatistic(Statistic.Average)
                        .withThreshold(alarm.getThreshold())
                        .withComparisonOperator(ComparisonOperator.fromValue(alarm.getComparator().toString()))
                        .withAlarmActions(alarmTopicArn)
        );
    }

    public void delete(Alarm alarm) {
        cloudWatch.deleteAlarms(
                new DeleteAlarmsRequest().withAlarmNames(alarm.getId()));
    }

    public AlarmStateValue getAlarmStateValue(Alarm alarm) {
        var result = cloudWatch.describeAlarms(
                new DescribeAlarmsRequest().withAlarmNames(alarm.getId()));
        String valueString = result.getMetricAlarms().get(0).getStateValue();
        return AlarmStateValue.valueOf(valueString);
    }
}
