package org.metrichosu.mcollect

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.metrichosu.mcollect.dto.Metric
import org.metrichosu.mcollect.parser.getParser

// Metric Input
data class Input(val metric: Metric)
class Output()

class ExternalMetricCollector : RequestHandler<Input, Output> {
    override fun handleRequest(input: Input?, context: Context?): Output {
        input?.metric?.let { metric ->
            getParser(metric)
                .parseDataFromSource(metric)
                .let { // 저장하는거 추가
                }

        }
        return Output()
    }
}
