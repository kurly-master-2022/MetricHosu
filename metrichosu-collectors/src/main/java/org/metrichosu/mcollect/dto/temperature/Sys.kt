package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Sys(
    @JsonProperty("country")
    val country: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("sunrise")
    val sunrise: Int,
    @JsonProperty("sunset")
    val sunset: Int,
    @JsonProperty("type")
    val type: Int
)