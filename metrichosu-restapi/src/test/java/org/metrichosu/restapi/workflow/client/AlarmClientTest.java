package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.metrichosu.restapi.testconfig.AWSClientsConfig;
import org.metrichosu.restapi.workflow.entity.Alarm;
import org.metrichosu.restapi.workflow.entity.AlarmComparator;
import org.metrichosu.restapi.workflow.entity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ActiveProfiles("test")
@Import({AWSClientsConfig.class})
@ExtendWith(SpringExtension.class)
class AlarmClientTest {

    @Autowired
    AmazonCloudWatch cloudWatch;

    AlarmClient client;

    @BeforeEach
    void init() {
        client = new AlarmClient(cloudWatch);
    }

    @Test
    void register() {
        client.register(Alarm.builder()
                .comparator(AlarmComparator.GreaterThanOrEqualToThreshold)
                .threshold(100)
                .metric(Metric.builder()
                        .id(RandomString.make(7))
                        .name("안녕-메트릭호수-메트릭")
                        .build())
                .build());
    }
}