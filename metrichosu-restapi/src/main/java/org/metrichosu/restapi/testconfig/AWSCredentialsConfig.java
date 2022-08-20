package org.metrichosu.restapi.testconfig;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Profile("test")
@PropertySource("credentials.properties")
@Configuration
public class AWSCredentialsConfig {

    @Primary
    @Bean
    AWSCredentialsProvider credentialsProvider(@Value("${AWS.accessKey}") String accessKey,
                                               @Value("${AWS.secretKey}") String secretKey) {

        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }
}
