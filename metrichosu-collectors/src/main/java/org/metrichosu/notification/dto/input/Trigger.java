package org.metrichosu.notification.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Trigger {

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
