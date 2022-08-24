package org.metrichosu.mcollect

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object Util {
	fun getTodayDateTime() = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(-9)))
}