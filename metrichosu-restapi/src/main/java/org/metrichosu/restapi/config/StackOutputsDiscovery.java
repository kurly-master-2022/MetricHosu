package org.metrichosu.restapi.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 프로젝트가 의존하는 어떤 클라우드 포메이션 스택에서
 * 스택 아웃풋 값을 색인해 가져오는 기능을 제공합니다.
 * @author jbinchoo
 * @since 2022/08/21
 */
@Component
public class StackOutputsDiscovery {

    private AmazonCloudFormation cloudFormation;
    private Map<String, String> outputs = new HashMap<>();

    public StackOutputsDiscovery(@Autowired AmazonCloudFormation cloudFormation,
                                 @Value("${dependent-stack-names}") String... stackNames) {

        this.cloudFormation = cloudFormation;
        this.loadOutputs(stackNames);
    }

    private void loadOutputs(String[] stackNames) {
        List<Stack> stacks = new ArrayList<>();

        for (String stackName : stackNames) {
            try {
                Stack stack = cloudFormation.describeStacks(
                        new DescribeStacksRequest().withStackName(stackName))
                        .getStacks()
                        .get(0);
                stacks.add(stack);
            } catch (Exception ignored) {
            }
        }

        for (Stack stack : stacks) {
            for (Output o : stack.getOutputs())
                this.outputs.put(o.getOutputKey(), o.getOutputValue());
        }
    }

    /**
     *
     * @param outputName
     * @return
     */
    public String resolveOutput(String outputName) {
        if (this.outputs.containsKey(outputName))
            return this.outputs.get(outputName);
        throw new IllegalArgumentException(String.format("%s에 없는 아웃풋입니다.", this.outputs));
    }
}
