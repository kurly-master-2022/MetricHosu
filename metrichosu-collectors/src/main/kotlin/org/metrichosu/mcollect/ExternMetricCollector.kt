package org.metrichosu.mcollect

import org.metrichosu.mcollect.adapter.CloudWatchApi
import org.metrichosu.mcollect.parser.external.getExternalParser

class ExternMetricCollector {
    fun handle(input: Map<String, String>?) {
        val cloudWatchApi = CloudWatchApi()
        val keyName = "mid"

        val mid = input!![keyName]!!

        getExternalParser(mid).parseDataFromSource(mid)
                .let { metricValue ->
                    cloudWatchApi.postToCloudWatch(metricValue)
                    println("$metricValue 등록 완료")
                }
    }
}