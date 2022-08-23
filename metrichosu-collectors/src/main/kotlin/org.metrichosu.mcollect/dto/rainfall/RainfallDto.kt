package org.metrichosu.mcollect.dto.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

data class RainfallDto(
    @JsonProperty("getRainfallInfo")
    val rainfallInfo: RainfallInfo
)