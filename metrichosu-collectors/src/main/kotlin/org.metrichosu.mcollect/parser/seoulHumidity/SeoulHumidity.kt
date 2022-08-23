package org.metrichosu.mcollect.parser.seoulHumidity

import org.metrichosu.mcollect.parser.seoulTemperature.SeoulTemperatureDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.ExternalSourceParser
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class SeoulHumidity : ExternalSourceParser {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
    private val url = "https://api.openweathermap.org/data/3.0/onecall?lat=37.564&lon=127&units=metric&exclude=minutely,hourly,daily&appid=521912d5d02f3ba9098e56bc5952c7e7"

    override fun parseDataFromSource(mid: String): MetricValue {
        val getRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        val requestResult = this.httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString())
            .let {
                ObjectMapper().readValue(it.body(), SeoulTemperatureDto::class.java)
            }

        return MetricValue(
            mid,
            Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(9))),
            requestResult.currentWeatherInfo.humidity
        )
    }
}