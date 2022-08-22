package org.metrichosu.restapi.workflow.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Data
public class CollectorInput {

    private String mid;

    public CollectorInput(Metric metric) {
        this.mid = metric.getId();
    }

    public String asJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize a MetricInput", e);
        }
    }
}
