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
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<String> main() {
        return ResponseEntity.ok("Metric Hosu REST API");
    }

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok().build();
    }
}
