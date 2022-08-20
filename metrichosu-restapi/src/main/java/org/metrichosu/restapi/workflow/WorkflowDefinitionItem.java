package org.metrichosu.restapi.workflow;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * @author jbinchoo
 * @since 2022/08/21
 */
@DynamoDBTable(tableName = "MetricHosuTable")
public class WorkflowDefinitionItem {

    @DynamoDBHashKey
    private String pk;

    @DynamoDBRangeKey
    private String sk;
}
