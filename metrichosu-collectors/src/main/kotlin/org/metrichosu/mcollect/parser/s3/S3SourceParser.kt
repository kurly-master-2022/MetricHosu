package org.metrichosu.mcollect.parser.s3

import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.s3.price.DailyViewsParser
import org.metrichosu.mcollect.parser.s3.price.PriceParser

interface S3SourceParser {
	fun parseDataFromRow(condition: String, row: String, dateString:String): MetricValue
}

fun getS3Parser(condition: String) = when (condition) {
	"price" -> PriceParser()
	"daily_views" -> DailyViewsParser()
	else -> throw IllegalArgumentException("잘못된 condition(${condition}) 이 주어졌습니다.")
}
