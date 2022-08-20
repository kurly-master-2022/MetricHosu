package org.metrichosu.restapi.testconfig;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Import(AWSCredentialsConfig.class)
@TestConfiguration
public class AWSClientsConfig {

    @Autowired
    AWSCredentialsProvider credentialsProvider;

    @Bean
    public AmazonCloudWatch cloudWatch() {
        var builder = AmazonCloudWatchClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }

    @Bean
    public AmazonCloudWatchEvents events() {
        var builder = AmazonCloudWatchEventsClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }

    @Bean
    public AmazonCloudFormation cloudFormation() {
        var builder = AmazonCloudFormationClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }
}
