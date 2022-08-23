package org.metrichosu.mcollect

import org.metrichosu.mcollect.adapter.CloudWatchApi
import org.metrichosu.mcollect.parser.external.getParser

class ExternMetricCollector {
    fun handle(input: Map<String, String>?) {
        val cloudWatchApi = CloudWatchApi()
        val keyName = "mid"

        val mid = input!![keyName]!!

        getParser(mid).parseDataFromSource(mid)
                .let { metricValue ->
                    cloudWatchApi.postToCloudWatch(metricValue)
                    println("$metricValue(Metric Value 등록 완료)")
                }
    }
}