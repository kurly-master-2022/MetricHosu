package org.metrichosu.mcollect.dto.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

data class Item(
    @JsonProperty("accRain")
    val value: Double,
    @JsonProperty("accRainDt")
    val accRainDt: String,
    @JsonProperty("clientId")
    val clientId: String,
    @JsonProperty("clientName")
    val clientName: String,
    @JsonProperty("lastRainDt")
    val lastRainDateTime: String,
    @JsonProperty("level12")
    val level12: String,
    @JsonProperty("level6")
    val level6: String,
    @JsonProperty("timeDay")
    val timeDay: String
)