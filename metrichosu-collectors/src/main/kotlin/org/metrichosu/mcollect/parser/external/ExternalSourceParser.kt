package org.metrichosu.mcollect.parser.external

import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.external.appleSaleAmount.AppleSaleAmount
import org.metrichosu.mcollect.parser.external.dailyKurlyWarehouseFee.DailyKurlyWarehouseFee
import org.metrichosu.mcollect.parser.external.rainfall.RainfallParser
import org.metrichosu.mcollect.parser.external.seoulClouds.SeoulClouds
import org.metrichosu.mcollect.parser.external.seoulHumanTemperature.SeoulHumanTemperature
import org.metrichosu.mcollect.parser.external.seoulHumidity.SeoulHumidity
import org.metrichosu.mcollect.parser.external.seoulTemperature.SeoulTemperature
import org.metrichosu.mcollect.parser.external.seoulWindSpeed.SeoulWindSpeed
import org.metrichosu.mcollect.parser.external.temperature.TemperatureParser

interface ExternalSourceParser {
	fun parseDataFromSource(mid: String): MetricValue
}

fun getExternalParser(mid: String) = when (mid) {
	"apple-sales-amount" -> AppleSaleAmount()
	"daily-kurly-warehouse-fee" -> DailyKurlyWarehouseFee()
	"rainfall" -> RainfallParser()
	"seoul-clouds" -> SeoulClouds()
	"seoul-human-temperature" -> SeoulHumanTemperature()
	"seoul-humidity" -> SeoulHumidity()
	"seoul-temperature" -> SeoulTemperature()
	"seoul-wind-speed" -> SeoulWindSpeed()
	"temperature" -> TemperatureParser()
	else -> throw IllegalArgumentException("잘못된 metric name(${mid}) 이 주어졌습니다.")
}
