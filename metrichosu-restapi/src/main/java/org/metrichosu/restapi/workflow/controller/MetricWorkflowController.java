package org.metrichosu.restapi.workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.service.WorkflowService;
import org.metrichosu.restapi.workflow.dto.MetricMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class MetricWorkflowController {

    private final WorkflowService service;

    @PostMapping
    public ResponseEntity<?> createWorkflow(@Valid @RequestBody MetricMetadata metadata) {
        log.debug(metadata.toString());
        System.out.println(metadata.toString());
        service.createWorkflow(metadata.toEntity());
        return ResponseEntity.ok().build();
    }

    // TODO: 무엇을 반환할 것인가?
    @GetMapping("/{mid}")
    public MetricMetadata describeWorkflow(@PathVariable("mid") String metricId) {
        return new MetricMetadata(service.findByMetricId(metricId));
    }
}
