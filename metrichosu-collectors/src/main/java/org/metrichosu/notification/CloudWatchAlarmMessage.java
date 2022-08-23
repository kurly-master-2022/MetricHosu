package org.metrichosu.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * CloudWatch가 SNS에게 발행해 준 알람 메시지의 POJO
 * <p> - Converted By <a href="https://json2csharp.com">https://json2csharp.com</a> </p>
 * @author jbinchoo
 * @since 2022/08/23
 */
@Data
public class CloudWatchAlarmMessage {

    @JsonProperty("AlarmName")
    private String alarmName;

    @JsonProperty("AlarmDescription")
    private Object alarmDescription;

    @JsonProperty("AWSAccountId")
    private String aWSAccountId;

    @JsonProperty("AlarmConfigurationUpdatedTimestamp")
    private DateTime alarmConfigurationUpdatedTimestamp;

    @JsonProperty("NewStateValue")
    private String newStateValue;

    @JsonProperty("NewStateReason")
    private String newStateReason;

    @JsonProperty("StateChangeTime")
    private DateTime stateChangeTime;

    @JsonProperty("Region")
    private String region;

    @JsonProperty("AlarmArn")
    private String alarmArn;

    @JsonProperty("OldStateValue")
    private String oldStateValue;

    @JsonProperty("OKActions")
    private ArrayList<Object> oKActions;

    @JsonProperty("AlarmActions")
    private ArrayList<String> alarmActions;

    @JsonProperty("InsufficientDataActions")
    private ArrayList<Object> insufficientDataActions;

    @JsonProperty("Trigger")
    private Trigger trigger;
}

@Data
class Trigger {

    @JsonProperty("MetricName")
    private String metricName;

    @JsonProperty("Namespace")
    private String namespace;

    @JsonProperty("StatisticType")
    private String statisticType;

    @JsonProperty("Statistic")
    private String statistic;

    @JsonProperty("Unit")
    private Object unit;

    @JsonProperty("Dimensions")
    private ArrayList<Object> dimensions;

    @JsonProperty("Period")
    private int period;

    @JsonProperty("EvaluationPeriods")
    private int evaluationPeriods;

    @JsonProperty("ComparisonOperator")
    private String comparisonOperator;

    @JsonProperty("Threshold")
    private double threshold;

    @JsonProperty("TreatMissingData")
    private String treatMissingData;

    @JsonProperty("EvaluateLowSampleCountPercentile")
    private String evaluateLowSampleCountPercentile;
}
