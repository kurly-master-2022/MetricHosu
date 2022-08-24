package org.metrichosu.mcollect.parser.external.rainfall

import com.fasterxml.jackson.databind.ObjectMapper
import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.external.ExternalSourceParser
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class RainfallParser : ExternalSourceParser {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
    private val rainfallUrl =
        "http://apis.data.go.kr/6260000/BusanRainfalldepthInfoService/getRainfallInfo?serviceKey=8KyZomTTp%2BQRbESv%2BQXJQusqYBCrloGOPD%2FW%2BIK%2BdcrecxGF9HsdqSDnUhj2rEkSNHvNgKWO8V%2BdfKP4OM8gxg%3D%3D&pageNo=1&numOfRows=1000&resultType=json"

    override fun parseDataFromSource(mid: String): MetricValue {
        val getRequest = HttpRequest.newBuilder()
            .uri(URI.create(rainfallUrl))
            .build()

        val requestResult = this.httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString()).body()
            .let { ObjectMapper().readValue(it, RainfallDto::class.java) }

        return requestResult.rainfallInfo.body.items.item.last()
            .let { MetricValue(mid, Util.getTodayDateTime(), it.value) }
    }
}