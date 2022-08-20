package org.metrichosu.restapi.testconfig;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Profile("test")
@Import(AWSCredentialsConfig.class)
@Configuration
public class AWSClientsConfig {

    @Autowired
    AWSCredentialsProvider credentialsProvider;

    @Primary
    @Bean
    public AmazonCloudWatch cloudWatchTest() {
        var builder = AmazonCloudWatchClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }

    @Primary
    @Bean
    public AmazonCloudWatchEvents eventsTest() {
        var builder = AmazonCloudWatchEventsClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }

    @Primary
    @Bean
    public AmazonCloudFormation cloudFormationTest() {
        var builder = AmazonCloudFormationClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }

    @Primary
    @Bean
    public AmazonDynamoDB dynamoDBTest() {
        var builder = AmazonDynamoDBClientBuilder.standard();
        builder.setRegion("ap-northeast-2");
        builder.setCredentials(credentialsProvider);
        return builder.build();
    }
}
