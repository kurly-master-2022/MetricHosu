package org.metrichosu.restapi.config;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClient;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Configuration
public class CloudWatchConfig {

    @Bean
    public AmazonCloudWatchEvents events() {
        return AmazonCloudWatchEventsClientBuilder.defaultClient();
    }

    @Bean
    public AmazonCloudWatch cloudWatch() {
        return AmazonCloudWatchClientBuilder.defaultClient();
    }
}
