package org.metrichosu.restapi.workflow.controller;

import lombok.RequiredArgsConstructor;
import org.metrichosu.restapi.workflow.dto.input.SubscriberListForm;
import org.metrichosu.restapi.workflow.entity.Subscription;
import org.metrichosu.restapi.workflow.entity.SubscriptionRow;
import org.metrichosu.restapi.workflow.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping("/subscribers")
    public ResponseEntity<List<SubscriptionRow>> putSubscriber(@RequestBody SubscriberListForm form) {
        Subscription subscription = form.toEntity();
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(subscriptionService.putSubscription(subscription));
    }

    @DeleteMapping("/subscribers")
    public List<SubscriptionRow> deleteSubscriber(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "destination", required = false) String destination,
                                 @RequestBody String arn) {

        return subscriptionService.deleteSubscriber(name, destination, arn);
    }

    @PostMapping("/subscribers")
    public ResponseEntity<List<SubscriptionRow>> appendSubscriberMetric(@RequestParam(value = "name", required = false) String name,
                                                                        @RequestParam(value = "destination", required = false) String destination,
                                                                        @RequestParam(value = "mid") String metricId,
                                                                        @RequestBody String arn) {
        assert arn != null;
        List<SubscriptionRow> deleted = subscriptionService.deleteSubscriber(name, destination, arn);

        List<String> metricIds = new ArrayList<>();
        metricIds.add(metricId);
        for (SubscriptionRow r : deleted) metricIds.add(r.getMetricId());

        SubscriptionRow metadata = deleted.get(0);
        Subscription subscription = new Subscription(null, metadata.getName(), metricIds,
                metadata.getProtocol(), metadata.getDestination());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(subscriptionService.putSubscription(subscription));
    }

    @DeleteMapping("/subscription")
    public SubscriptionRow deleteSubscription(@RequestParam("uuid") String uuid) {
        return subscriptionService.deleteById(uuid);
    }
}
