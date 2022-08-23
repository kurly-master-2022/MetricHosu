package org.metrichosu.mcollect.dto

data class Metric(
    val mid: Long,
    val name: String,
    val metricSourceType: String,
    val metricSourceUri: String,
    val schedule: String,
    val isScheduled: Boolean,
    val s3ObjectKey: String,
    val threshold: Number,
    val thresholdDriection: Boolean // 추후 수정
) {
}
