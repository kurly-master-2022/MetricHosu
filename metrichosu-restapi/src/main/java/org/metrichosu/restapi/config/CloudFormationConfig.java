package org.metrichosu.restapi.config;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Configuration
public class CloudFormationConfig {

    @Bean
    public AmazonCloudFormation cloudFormation() {
        return AmazonCloudFormationClientBuilder.defaultClient();
    }
}
