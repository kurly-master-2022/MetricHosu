package org.metrichosu.mcollect.parser.s3.price

import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.s3.S3SourceParser

class PriceParser : S3SourceParser {
    override fun parseDataFromSource(mid: String, price: Double): MetricValue {
        return MetricValue(mid, Util.getTodayDateTime(), price)
    }
}