package org.metrichosu.mcollect.parser

import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue

interface S3SourceParser {
    // S3 arn혹은 다른걸로 수정
    fun parseDataFromSource(fileName: String): MetricValue
}