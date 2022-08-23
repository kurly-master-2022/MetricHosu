package org.metrichosu.restapi.workflow.entity.trigger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.metrichosu.restapi.workflow.entity.metric.Metric;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
public class CollectorInput {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String mid;

    public CollectorInput(Metric metric) {
        this.mid = metric.getId();
    }

    public String asJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("메트릭 수집기용 입력을 생성하는 데 실패했습니다.", e);
        }
    }
}
