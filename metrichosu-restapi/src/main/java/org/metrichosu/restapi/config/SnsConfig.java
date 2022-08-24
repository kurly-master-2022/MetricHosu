package org.metrichosu.restapi.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
public class SnsConfig {

    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.defaultClient();
    }
}
