package org.metrichosu.mcollect.parser.appleSaleAmount

import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.ExternalSourceParser
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class AppleSaleAmount: ExternalSourceParser {
    /**
     * 이 파서는, 컬리에 "사과" 라는 상품이 있다고 가정할 때, 그 상품의 시간별 판매량을 나타냅니다.
     *
     * 데이터는 크롤링 혹은 컬리 서버 내부에 있기 때문에, 여기선 1 ~ 100 사이의 랜덤한 값으로 리턴하도록 합니다.
     * */

    override fun parseDataFromSource(metric: Metric): MetricValue {
        val salesAmount = (1..100).random()

        return MetricValue(
            metric.mid,
            Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(9))),
            salesAmount.toDouble()
        )
    }
}