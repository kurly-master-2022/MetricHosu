package org.metrichosu.restapi.workflow.client;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.metrichosu.restapi.testconfig.AWSClientsConfig;
import org.metrichosu.restapi.workflow.entity.alarm.Alarm;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmComparator;
import org.metrichosu.restapi.workflow.entity.metric.Metric;
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
class AlarmClientTest {

    @Autowired
    AmazonCloudFormation cloudFormation;

    @Autowired
    AmazonCloudWatch cloudWatch;

    AlarmClient client;

    @BeforeEach
    void init() {
        String alarmTopicArn = new StackOutputsDiscovery(cloudFormation, "metrichosu-collectors")
                .resolveOutput("AlarmTopic");
        client = new AlarmClient(cloudWatch);
        client.setAlarmTopicArn(alarmTopicArn);
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