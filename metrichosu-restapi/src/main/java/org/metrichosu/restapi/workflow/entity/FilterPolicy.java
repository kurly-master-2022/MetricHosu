package org.metrichosu.restapi.workflow.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Getter
public class FilterPolicy {

    private final Set<String> mid = new HashSet<>();

    public FilterPolicy(List<String> targetMetrics) {
        mid.addAll(targetMetrics);
    }

    public String asJson(ObjectMapper mapper) throws JsonProcessingException {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("{} 필터 정책 생성 중 직렬화 불가.", mid, e);
            throw e;
        }
    }
}
