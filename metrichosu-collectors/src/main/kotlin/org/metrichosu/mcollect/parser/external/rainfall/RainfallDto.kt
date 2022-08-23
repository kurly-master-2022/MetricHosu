package org.metrichosu.mcollect.parser.external.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

data class RainfallDto(
    @JsonProperty("getRainfallInfo")
    val rainfallInfo: RainfallInfo
)

data class Items(
        @JsonProperty("item")
        val item: List<Item>
)

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

data class RainfallInfo(
        @JsonProperty("body")
        val body: Body,
        @JsonProperty("header")
        val header: Header
)

data class Header(
        @JsonProperty("resultCode")
        val resultCode: String,
        @JsonProperty("resultMsg")
        val resultMsg: String
)

data class Body(
        @JsonProperty("items")
        val items: Items,
        @JsonProperty("numOfRows")
        val numOfRows: Int,
        @JsonProperty("pageNo")
        val pageNo: Int,
        @JsonProperty("totalCount")
        val totalCount: Int
)