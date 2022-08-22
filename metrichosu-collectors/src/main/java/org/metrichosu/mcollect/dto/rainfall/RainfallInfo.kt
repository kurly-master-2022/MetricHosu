package org.metrichosu.mcollect.dto.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

data class RainfallInfo(
    @JsonProperty("body")
    val body: Body,
    @JsonProperty("header")
    val header: Header
)