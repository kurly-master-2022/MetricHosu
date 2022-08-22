package org.metrichosu.restapi.workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.metrichosu.restapi.workflow.dto.input.MetricForm;
import org.metrichosu.restapi.workflow.dto.output.WorkflowDefinitionDto;
import org.metrichosu.restapi.workflow.entity.WorkflowDefinition;
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

    @PutMapping
    public WorkflowDefinitionDto putWorkflow(@Valid @RequestBody MetricForm metadata) {
        WorkflowDefinition definition = this.workflowService.putWorkflow(metadata.toWorkflowDefinition());
        return returnDto(definition);
    }

    @PutMapping("/{mid}")
    public WorkflowDefinitionDto enableTrigger(@PathVariable("mid") String metricId,
                                               @RequestParam("enabled") boolean enabled) {
        WorkflowDefinition definition = this.workflowService.putWorkflowState(metricId, enabled);
        return returnDto(definition);
    }

    @GetMapping("/{mid}")
    public WorkflowDefinitionDto describeWorkflow(@PathVariable("mid") String metricId) {
        WorkflowDefinition definition = this.workflowService.describeOriginWorkflow(metricId);
        return returnDto(definition);
    }


    private WorkflowDefinitionDto returnDto(WorkflowDefinition definition) {
        log.debug(definition.toString());
        return new WorkflowDefinitionDto(definition);
    }

    @DeleteMapping("/{mid}")
    public ResponseEntity<?> deleteWorkflow(@PathVariable("mid") String metricId) {
        workflowService.deleteOriginWorkflow(metricId);
        return ResponseEntity.ok().build();
    }
}
