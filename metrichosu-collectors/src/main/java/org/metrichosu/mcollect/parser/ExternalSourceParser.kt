package org.metrichosu.mcollect.parser

import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.rainfall.RainfallParser
import org.metrichosu.mcollect.parser.temperature.TemperatureParser

interface ExternalSourceParser {
    fun parseDataFromSource(metric: Metric): MetricValue
}

fun getParser(metric: Metric): ExternalSourceParser {
    return when(metric.name){
        "rainfall" -> RainfallParser()
        "temperature" -> TemperatureParser()
        else -> throw IllegalArgumentException("잘못된 metric name 이 주어졌습니다.")
    }
}