package org.metrichosu.mcollect

import java.util.function.Function as Function
import org.metrichosu.mcollect.adapter.Adapter
import org.metrichosu.mcollect.dto.Metric
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class MetricCollector {

    @Bean
    open fun request(
        adapter: Adapter
        // dynamoDB bean 도 주입받아야 함.
        // dynamo: DynamoDB 처럼...
    ): Function<String, Boolean>{
        return Function<String, Boolean>{ metricName ->


            // metricName 을 토대로 메트릭을 찾았다고 가정.
            // 원래대로라면 이렇게 작동
            // val metric: Metric = dynamo.getMetricByName(metricName)
            val metric = Metric(1, "테스트", "테스트", "테스트", "테스트", false, "test", 12.3, false)

            ExternalMetricCollector().handleRequest(Input(metric), null)
                .let {

                    // 결과가 잘 실행되었을 경우 true return
                    true
                }
        }
    }
}