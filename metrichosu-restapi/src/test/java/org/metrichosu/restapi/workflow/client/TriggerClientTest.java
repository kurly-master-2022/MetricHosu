package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.metrichosu.restapi.testconfig.AWSClientsConfig;
import org.metrichosu.restapi.config.MetricCollectorArnResolver;
import org.metrichosu.restapi.workflow.entity.CollectorTrigger;
import org.metrichosu.restapi.workflow.entity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ActiveProfiles("test")
@Import({AWSClientsConfig.class})
@ExtendWith(SpringExtension.class)
class TriggerClientTest {

    @Autowired
    AmazonCloudWatchEvents events;

    @Autowired
    AmazonCloudFormation cloudFormation;

    String lambdaArn;
    TriggerClient client;

    @BeforeEach
    void init() {
        lambdaArn = new MetricCollectorArnResolver(cloudFormation).resolve();
        client = new TriggerClient(events);
        client.setMetricCollectorArn(lambdaArn);
        System.out.println("ARN=" + lambdaArn);
    }

    @Test
    void register() {
        client.putTrigger(CollectorTrigger.builder()
                .metric(Metric.builder()
                        .id(RandomString.make(7))
                        .name("안녕-메트릭호수-규칙")
                        .build())
                .schedCron("0 9 * * ? *")
                .build());
    }
}