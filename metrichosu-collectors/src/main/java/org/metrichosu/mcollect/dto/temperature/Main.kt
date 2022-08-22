package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class Main(
    @JsonProperty("feels_like")
    val feelsLike: Double,
    @JsonProperty("humidity")
    val humidity: Int,
    @JsonProperty("pressure")
    val pressure: Int,
    @JsonProperty("temp")
    val temp: Double,
    @JsonProperty("temp_max")
    val tempMax: Double,
    @JsonProperty("temp_min")
    val tempMin: Double
)