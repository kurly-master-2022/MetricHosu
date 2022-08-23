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
@Component
@RequiredArgsConstructor
public class AlarmClient {

    private final AmazonCloudWatch cloudWatch;

    @Autowired
    @Qualifier("alarm-topic-arn")
    private String alarmTopicArn;

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

    public void setAlarmTopicArn(String alarmTopicArn) {
        this.alarmTopicArn = alarmTopicArn;
    }
}
