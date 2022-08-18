package org.metrichosu.mcollect;

import com.amazonaws.services.lambda.runtime.events.S3Event;

public class S3MetricCollector {

    public void handle(S3Event event) {
        System.out.println("Hello World!");
    }
}
