package org.metrichosu.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author jbinchoo
 * @since 2022/08/23
 */
@Configuration
public class DependentResourceArnConfig {

    @Autowired
    private StackOutputsResolver resolver;

    @Lazy
    @Bean(name = "extern-metric-collector-arn")
    public String externMetricCollectorArn(@Value("${metrichosu-collectors.extern-metric-collector}") String outputName) {
        return this.resolver.resolveOutput(outputName);
    }

    @Lazy
    @Bean(name = "alarm-topic-arn")
    public String alarmTopicArn(@Value("${metrichosu-collectors.alarm-topic}") String outputName) {
        return this.resolver.resolveOutput(outputName);
    }
}
