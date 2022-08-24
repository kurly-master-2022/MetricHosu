package org.metrichosu.mcollect.parser.s3.price

import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.s3.S3SourceParser

class DailyViewsParser:S3SourceParser {
	override fun parseDataFromRow(condition: String, row: String, dateString:String): MetricValue {
		val (productName, daily_views) = row.split(",")
		val mid = "${productName}_${condition}"
		return MetricValue(mid, Util.getDateFromDateString(dateString), daily_views.toDouble())
	}
}