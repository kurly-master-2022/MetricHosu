package org.metrichosu.mcollect.dto.rainfall


import com.fasterxml.jackson.annotation.JsonProperty

data class Items(
    @JsonProperty("item")
    val item: List<Item>
)