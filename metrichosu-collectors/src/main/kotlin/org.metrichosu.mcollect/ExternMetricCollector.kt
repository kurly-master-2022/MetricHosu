package org.metrichosu.mcollect

import org.metrichosu.mcollect.adapter.CloudWatchApi
import org.metrichosu.mcollect.parser.getParser

class ExternMetricCollector {
    fun handle(input: Map<String, String>?) {
        val cloudWatchApi = CloudWatchApi()
        val keyName = "metric"

        val mid = input!![keyName]!!

        getParser(mid).parseDataFromSource(mid)
                .let { metricValue ->
                    cloudWatchApi.postToCloudWatch(metricValue)
                }
    }
}