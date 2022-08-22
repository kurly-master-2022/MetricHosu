package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Weather(
    @JsonProperty("description")
    val description: String,
    @JsonProperty("icon")
    val icon: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("main")
    val main: String
)