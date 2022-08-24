package org.metrichosu.restapi.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.client.AlarmClient;
import org.metrichosu.restapi.workflow.client.SnsClient;
import org.metrichosu.restapi.workflow.client.TriggerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@Setter // ConfigurationProperties의 필드 주입을 가동하려면 setter가 꼭 필요하다!
@Configuration
@ConfigurationProperties(prefix="autodi", ignoreInvalidFields = true)
public class CloudClientConfig {

    private Map<String, List<String>> stackOutputs;
    private Map<String, String> arns;

    @Autowired
    private AmazonCloudFormation cloudFormation;

    public void initialize() {
        if (this.arns == null) {
            log.info("Let's begin {}", stackOutputs);
            this.arns = new HashMap<>();
            this.register(this.arns, this.stackOutputs);
            log.info("Let's end {}", this.arns);
        }
    }

    private void register(Map<String, String> arns, Map<String, List<String>> queries) {
        for (String stackName : queries.keySet()) {
            try {
                Stack cloudFormationStack = this.findStack(stackName);
                Set<String> stackOutputQueries = new HashSet<>(stackOutputs.get(stackName));
                registerIfQueried(stackName, stackOutputQueries, cloudFormationStack.getOutputs(), arns);
            } catch (Exception e) {
                log.error("{} 스택의 아웃풋을 가져오지 못 했습니다.", stackName, e);
            }
        }
    }

    private void registerIfQueried(String stackName, Set<String> queries, List<Output> outputs, Map<String, String> arns) {
        outputs.stream().filter(o-> queries.contains(o.getOutputKey()))
                .forEach(o-> arns.put(o.getOutputKey(), o.getOutputValue()));
    }

    private Stack findStack(String stackName) {
        return cloudFormation.describeStacks(new DescribeStacksRequest()
                .withStackName(stackName)).getStacks().get(0);
    }

    @Bean
    public AmazonCloudWatchEvents events() {
        return AmazonCloudWatchEventsClientBuilder.defaultClient();
    }

    @Bean
    public AmazonCloudWatch cloudWatch() {
        return AmazonCloudWatchClientBuilder.defaultClient();
    }

    @Lazy
    @Bean
    public AlarmClient alarmClient(@Autowired AmazonCloudWatch cloudWatch) {
        this.initialize();
        return new AlarmClient(cloudWatch, this.arns.get("AlarmTopic"));
    }

    @Lazy
    @Bean
    public TriggerClient triggerClient(@Autowired AmazonCloudWatchEvents events) {
        this.initialize();
        return new TriggerClient(events, this.arns.get("ExternMetricCollectorFunction"));
    }

    @Lazy
    @Bean
    public SnsClient snsClient(@Autowired AmazonSNS amazonSNS) {
        this.initialize();
        return new SnsClient(amazonSNS, this.arns.get("AlarmMessageTopic"));
    }
}
