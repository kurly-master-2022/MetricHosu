package org.metrichosu.mcollect.parser.external.seoulHumanTemperature

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SeoulHumanTemperatureDto(
    @JsonProperty("current") val currentWeatherInfo: CurrentWeatherInfo
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrentWeatherInfo(
    @JsonProperty("temp") val temperature: Double,
    @JsonProperty("feels_like") val humanTemperature: Double,
    @JsonProperty("humidity") val humidity: Double,
    @JsonProperty("wind_speed") val windSpeed: Double,
    @JsonProperty("clouds") val clouds: Double,
)