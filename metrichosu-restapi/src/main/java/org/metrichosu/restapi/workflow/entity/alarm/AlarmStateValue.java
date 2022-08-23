package org.metrichosu.restapi.workflow.entity.alarm;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
public enum AlarmStateValue {
    OK, ALARM, INSUFFICIENT_DATA;

    public boolean stringEquals(String value) {
        try {
            AlarmStateValue other = AlarmStateValue.valueOf(value);
            return other.equals(this);
        } catch (IllegalArgumentException ignored) {
        }
        return false;
    }
}
