package org.metrichosu.mcollect.dto.temperature


import com.fasterxml.jackson.annotation.JsonProperty

data class TemperatureDto(
    @JsonProperty("base")
    val base: String,
    @JsonProperty("clouds")
    val clouds: Clouds,
    @JsonProperty("cod")
    val cod: Int,
    @JsonProperty("coord")
    val coord: Coord,
    @JsonProperty("dt")
    val dt: Int,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("main")
    val main: Main,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("sys")
    val sys: Sys,
    @JsonProperty("timezone")
    val timezone: Int,
    @JsonProperty("visibility")
    val visibility: Int,
    @JsonProperty("weather")
    val weather: List<Weather>,
    @JsonProperty("wind")
    val wind: Wind
)