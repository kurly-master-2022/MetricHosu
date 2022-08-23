package org.metrichosu.mcollect.adapter

import com.amazonaws.services.cloudwatch.AmazonCloudWatch
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder
import com.amazonaws.services.cloudwatch.model.MetricDatum
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult
import com.amazonaws.services.cloudwatch.model.StandardUnit
import org.metrichosu.mcollect.dto.MetricValue

class CloudWatchApi {
    private val cloudWatch: AmazonCloudWatch = AmazonCloudWatchClientBuilder.defaultClient()
    private val namespace = "Custom Metric Demo"

    fun postToCloudWatch(
            metricValue: MetricValue
    ): PutMetricDataResult? {
        val metricData = MetricDatum()
                .withMetricName(metricValue.mid)
                .withUnit(StandardUnit.None)
                .withValue(metricValue.value)
                .withTimestamp(metricValue.datetime)
                .withStorageResolution(60)

        val request = PutMetricDataRequest()
                .withNamespace(namespace)
                .withMetricData(metricData)

        return cloudWatch.putMetricData(request)
    }
}