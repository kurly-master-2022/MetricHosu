package org.metrichosu.mcollect.parser

import org.metrichosu.mcollect.parser.appleSaleAmount.AppleSaleAmount
import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.dailyKurlyWarehouseFee.DailyKurlyWarehouseFee
import org.metrichosu.mcollect.parser.rainfall.RainfallParser
import org.metrichosu.mcollect.parser.seoulClouds.SeoulClouds
import org.metrichosu.mcollect.parser.seoulHumanTemperature.SeoulHumanTemperature
import org.metrichosu.mcollect.parser.seoulHumidity.SeoulHumidity
import org.metrichosu.mcollect.parser.seoulTemperature.SeoulTemperature
import org.metrichosu.mcollect.parser.seoulWindSpeed.SeoulWindSpeed
import org.metrichosu.mcollect.parser.temperature.TemperatureParser

interface ExternalSourceParser {
    fun parseDataFromSource(mid: String): MetricValue
}

fun getParser(mid: String): ExternalSourceParser {
    return when(mid){
        "apple-sales-amount" -> AppleSaleAmount()
        "daily-kurly-warehouse-fee" -> DailyKurlyWarehouseFee()
        "rainfall" -> RainfallParser()
        "seoul-clouds" -> SeoulClouds()
        "seoul-human-temperature" -> SeoulHumanTemperature()
        "seoul-humidity" -> SeoulHumidity()
        "seoul-temperature" -> SeoulTemperature()
        "seoul-wind-speed" -> SeoulWindSpeed()
        "temperature" -> TemperatureParser()
        else -> throw IllegalArgumentException("잘못된 metric name 이 주어졌습니다.")
    }
}