package org.metrichosu.restapi.workflow.entity.alarm;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
public enum AlarmComparator {
    GreaterThanOrEqualToThreshold,
    GreaterThanThreshold,
    LessThanThreshold,
    LessThanOrEqualToThreshold,
    LessThanLowerOrGreaterThanUpperThreshold,
    LessThanLowerThreshold,
    GreaterThanUpperThreshold
}
