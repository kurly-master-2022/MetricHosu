package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Coord(
    @JsonProperty("lat")
    val lat: Double,
    @JsonProperty("lon")
    val lon: Double
)