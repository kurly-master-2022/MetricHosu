package org.metrichosu.restapi.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Configuration
@AllArgsConstructor
public class MetricCollectorArnResolver {

    private static final String STACK_NAME = "metrichosu";
    private static final String OUTPUT_LABEL = "ExternMetricCollectorFunction";

    @Autowired
    private AmazonCloudFormation cloudFormation;

    @Lazy
    @Bean(name = "metric-collector-arn")
    public String resolve() {
        List<Stack> candidates = cloudFormation.describeStacks(
                new DescribeStacksRequest()
                        .withStackName(STACK_NAME)).getStacks();

        Stack metricHosuStack = candidates.get(0);
        for (Output o : metricHosuStack.getOutputs())
            if (o.getOutputKey().equals(OUTPUT_LABEL))
                return o.getOutputValue();

        throw new IllegalStateException("MetricHosu stack does not have ExternMetricCollector.");
    }
}
