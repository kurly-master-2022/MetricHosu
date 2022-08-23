package org.metrichosu.mcollect.parser.temperature

import com.fasterxml.jackson.databind.ObjectMapper
import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.dto.temperature.TemperatureDto
import org.metrichosu.mcollect.parser.ExternalSourceParser
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TemperatureParser: ExternalSourceParser {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
    private val rainfallUrl =
        "http://api.openweathermap.org/data/2.5/weather?lat=37.410090&lon=126.972738&appid=12d9fdd365edb81aa3cb04ed20bf4453&units=metric"

    override fun parseDataFromSource(mid: String): MetricValue {
        val getRequest = HttpRequest.newBuilder()
            .uri(URI.create(rainfallUrl))
            .build()

        val requestResult = this.httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString()).body()
            .let { ObjectMapper().readValue(it, TemperatureDto::class.java) }

        return requestResult.main.temp.let{ temperature ->
            MetricValue(mid, Util.getTodayDateTime(), temperature)
        }
    }
}