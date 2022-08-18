package org.metrichosu.restapi.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jbinchoo
 * @since 2022/08/18
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
