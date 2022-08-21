package org.metrichosu.restapi.workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.dto.MetricMetadata;
import org.metrichosu.restapi.workflow.dto.WorkflowDefinitionDto;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
import org.metrichosu.restapi.workflow.service.TriggerService;
import org.metrichosu.restapi.workflow.service.WorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@Slf4j
@RequestMapping("/workflow")
@RequiredArgsConstructor
@RestController
public class WorkflowController {

    private final WorkflowService workflowService;
    private final TriggerService triggerService;

    @PutMapping
    public ResponseEntity<?> putWorkflow(@Valid @RequestBody MetricMetadata metadata) {
        workflowService.create(metadata.toEntity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{mid}")
    public WorkflowDefinitionDto describeWorkflow(@PathVariable("mid") String metricId) {
        WorkflowDefinition definition = workflowService.find(metricId);
        return new WorkflowDefinitionDto(definition, triggerService.exchange(definition.getTrigger()));
    }

    @PostMapping("/trigger/{mid}")
    public WorkflowDefinitionDto enableTrigger(@PathVariable("mid") String metricId,
                                               @RequestParam("enabled") boolean b) {
        WorkflowDefinition definition = workflowService.find(metricId);
        triggerService.updateState(definition.getTrigger(), b);
        return new WorkflowDefinitionDto(definition, triggerService.exchange(definition.getTrigger()));
    }

    @DeleteMapping("/{mid}")
    public ResponseEntity<?> deleteWorkflow(@PathVariable("mid") String metricId) {
        workflowService.delete(metricId);
        return ResponseEntity.ok().build();
    }
}
