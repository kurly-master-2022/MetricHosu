package org.metrichosu.mcollect

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object Util {
	fun getTodayDateTime(): Date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(9)))
	fun getDateFromDateString(dateString: String): Date {
		val year = dateString.substring(0, 4).toInt()
		val month = dateString.substring(4, 6).toInt()
		val day = dateString.substring(6, 8).toInt()
		val hour = dateString.substring(8, 10).toInt()
		val minute = dateString.substring(10, 12).toInt()
		val second = dateString.substring(12, 14).toInt()
		return Date.from(LocalDateTime.of(year, month, day, hour, minute, second).toInstant(ZoneOffset.ofHours(9)))
	}
}