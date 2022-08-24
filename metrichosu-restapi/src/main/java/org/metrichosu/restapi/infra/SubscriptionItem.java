package org.metrichosu.restapi.infra;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import org.metrichosu.restapi.workflow.entity.SnsProtocol;
import org.metrichosu.restapi.workflow.entity.Subscription;
import org.metrichosu.restapi.workflow.entity.SubscriptionRow;

/**
 * @author jbinchoo
 * @since 2022/08/24
 */
@Data
@Builder
@DynamoDBTable(tableName = "metrichosu")
@NoArgsConstructor // required
@AllArgsConstructor
public class SubscriptionItem {

    @DynamoDBGeneratedUuid(value = DynamoDBAutoGenerateStrategy.CREATE)
    @DynamoDBHashKey
    private String pk;

    @DynamoDBAttribute(attributeName = "#Subscription#name")
    private String name;

    @DynamoDBAttribute(attributeName = "#Subscription#destination")
    private String destination;

    @DynamoDBAttribute(attributeName = "#Subscription#metricId")
    private String metricId;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "#Subscription#protocol")
    private SnsProtocol protocol;

    public SubscriptionRow toEntity() {
        return SubscriptionRow.builder()
                .uuid(this.pk)
                .name(this.name)
                .metricId(this.metricId)
                .protocol(this.protocol)
                .destination(this.destination)
                .build();
    }

    public static SubscriptionItem fromEntity(SubscriptionRow entity) {
        return SubscriptionItem.builder()
                .name(entity.getName())
                .metricId(entity.getMetricId())
                .protocol(entity.getProtocol())
                .destination(entity.getDestination())
                .build();
    }
}
