package org.metrichosu.mcollect.parser.s3.coupeng

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.metrichosu.mcollect.Util
import org.metrichosu.mcollect.dto.MetricValue
import org.metrichosu.mcollect.parser.s3.S3SourceParser

class AppleParser : S3SourceParser {
    private val productName = "apple"

    // s3 파일읽어오도록 수정
    override fun parseDataFromSource(fileName: String): MetricValue {
        lateinit var metricValue: MetricValue

        csvReader().open(fileName) {
            readAllAsSequence().forEach { row ->
                val (rowProductName, rowPrice) = row
                if (rowProductName == productName) {
                    // mid 어떻게 넣을 것인지
                    metricValue = MetricValue("apple-price", Util.getTodayDateTime(), rowPrice.toDouble())
                    return@open
                }
            }
        }

        return metricValue
    }

}