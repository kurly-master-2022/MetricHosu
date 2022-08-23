package org.metrichosu.mcollect

import com.amazonaws.services.lambda.runtime.events.S3Event


class S3MetricCollector {
    fun handle(event: S3Event?) {
        println("Hello World!")
    }
}