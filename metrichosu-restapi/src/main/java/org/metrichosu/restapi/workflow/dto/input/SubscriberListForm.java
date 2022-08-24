package org.metrichosu.restapi.workflow.dto.input;

import lombok.Data;
import org.metrichosu.restapi.workflow.entity.SnsProtocol;
import org.metrichosu.restapi.workflow.entity.Subscription;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@Data
public class SubscriberListForm {

    @NotNull
    private String name;

    @NotNull
    private SnsProtocol protocol;

    @NotNull
    private String destination;

    @Size(min = 1, max = 30)
    List<String> metricIds;

    public Subscription toEntity() {
        return Subscription.builder()
                .name(name)
                .metricIds(metricIds)
                .protocol(protocol)
                .destination(destination).build();
    }
}
