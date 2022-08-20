package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.Statistic;
import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.workflow.entity.Alarm;
import org.springframework.stereotype.Component;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Component
@RequiredArgsConstructor
public class AlarmClient {

    private final AmazonCloudWatch cloudwath;

    public void register(Alarm alarm) {
        cloudwath.putMetricAlarm(
                new PutMetricAlarmRequest()
                        .withNamespace("metrichosu")
                        .withMetricName(alarm.getMetricId())
                        .withAlarmName(alarm.getId())
                        .withPeriod(alarm.getAssessPeriod())
                        .withEvaluationPeriods(alarm.getEvaluationPeriods())
                        .withStatistic(Statistic.Average)
                        .withThreshold(alarm.getThreshold())
                        .withComparisonOperator(ComparisonOperator.fromValue(alarm.getComparator().toString()))
        );
    }
}
