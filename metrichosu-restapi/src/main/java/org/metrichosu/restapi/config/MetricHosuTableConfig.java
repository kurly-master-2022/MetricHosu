package org.metrichosu.restapi.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Configuration;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@EnableDynamoDBRepositories(
        basePackages = "org.metrichosu.restapi.infra")
@Configuration
public class MetricHosuTableConfig {
}
