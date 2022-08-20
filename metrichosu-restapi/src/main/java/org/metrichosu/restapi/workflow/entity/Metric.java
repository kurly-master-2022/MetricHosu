package org.metrichosu.restapi.workflow.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@ToString
@Getter
@Builder
@RequiredArgsConstructor
public class Metric {

    private final String id;

    private final String name;
}
