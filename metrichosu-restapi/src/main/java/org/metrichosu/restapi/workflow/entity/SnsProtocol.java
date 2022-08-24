package org.metrichosu.restapi.workflow.entity;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
public enum SnsProtocol {
    HTTP("https"), HTTPS("https"),
    EMAIL("email"), EMAIL_JSON("email-json"),
    SMS("sms"), SQS("sqs"),
    APPLICATION("application"),
    LAMBDA("lambda"),
    FIREHOSE("firehose");

    private String value;

    SnsProtocol(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
