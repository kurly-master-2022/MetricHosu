package org.metrichosu.mcollect.dto.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

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