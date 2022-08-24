package org.metrichosu.mcollect.dto

import java.util.*

data class MetricValue(
    val mid: String,
    val datetime: Date,
    val value: Double
)