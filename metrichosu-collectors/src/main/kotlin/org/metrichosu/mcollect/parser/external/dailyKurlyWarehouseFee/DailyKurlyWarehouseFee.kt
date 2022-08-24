package org.metrichosu.mcollect.parser.external.dailyKurlyWarehouseFee

import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.external.ExternalSourceParser
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class DailyKurlyWarehouseFee : ExternalSourceParser {
	/**
	 * 이 파서는, 컬리의 일별 물류창고 유지비를 나타냅니다. 하루에 한 번 수집됩니다.
	 *
	 * 데이터는 크롤링 혹은 컬리 서버 내부에 있기 때문에, 여기선 2,000,000 ~ 10,000,000 사이의 랜덤한 값으로 리턴하도록 합니다.
	 * */

	override fun parseDataFromSource(mid: String): MetricValue {
		val warehouseFee = (2000000..10000000).random()

		return MetricValue(
				mid,
				Util.getTodayDateTime(),
				warehouseFee.toDouble()
		)
	}
}