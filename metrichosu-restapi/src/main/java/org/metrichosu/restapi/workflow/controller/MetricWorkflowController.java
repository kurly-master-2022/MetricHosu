package org.metrichosu.restapi.workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.service.WorkflowService;
import org.metrichosu.restapi.workflow.vo.MetricMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{mid}")
    public ResponseEntity<?> describeWorkflow(@PathVariable("mid") String metricId) {
        return ResponseEntity.ok("Hello MetricHosu!");
    }

    @PostMapping
    public ResponseEntity<?> createWorkflow(@RequestBody MetricMetadata metadata,
                                            BindingResult bResult) {
        log.info("bindingResult: {} / {}", bResult, metadata);
        boolean created = service.createWorkflow(metadata.getWorkflowDefinition());
        return created? ResponseEntity.ok(created)
                : ResponseEntity.badRequest().body("The metric already exists.");
    }
}
