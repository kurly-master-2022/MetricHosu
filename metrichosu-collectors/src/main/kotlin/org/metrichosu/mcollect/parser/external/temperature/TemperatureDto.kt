package org.metrichosu.mcollect.parser.external.temperature


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
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

@JsonIgnoreProperties(ignoreUnknown = true)
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class Wind(
        @JsonProperty("deg")
        val deg: Int,
        @JsonProperty("speed")
        val speed: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
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

@JsonIgnoreProperties(ignoreUnknown = true)
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coord(
        @JsonProperty("lat")
        val lat: Double,
        @JsonProperty("lon")
        val lon: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Clouds(
        @JsonProperty("all")
        val all: Int
)