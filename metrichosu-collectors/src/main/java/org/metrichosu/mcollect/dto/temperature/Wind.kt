package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Wind(
    @JsonProperty("deg")
    val deg: Int,
    @JsonProperty("speed")
    val speed: Double
)