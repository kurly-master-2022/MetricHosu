package org.metrichosu.restapi.workflow.dto.output;

import lombok.Data;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmStateValue;
import org.metrichosu.restapi.workflow.entity.alarm.AlarmStatus;

/**
 * 알람의 상태를 나타내는 출력 형식입니다.
 * @author jbinchoo
 * @since 2022/08/23
 */
@Data
public class AlarmStatusDto {
    // 알람의 아이디
    private String id;
    // 알람의 상태
    private AlarmStateValue state;
    // 알람이 감시하는 메트릭의 아이디
    private String metricId;

    public AlarmStatusDto(AlarmStatus entity) {
        this.id = entity.getId();
        this.metricId = entity.getMetricId();
        this.state = entity.getState();
    }
}
