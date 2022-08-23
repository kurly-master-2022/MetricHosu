package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Clouds(
    @JsonProperty("all")
    val all: Int
)