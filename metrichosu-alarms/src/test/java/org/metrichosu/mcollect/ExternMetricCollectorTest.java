package org.metrichosu.mcollect;

import org.junit.jupiter.api.Test;

public class ExternMetricCollectorTest {

  @Test
  public void successfulResponse() {
    ExternMetricCollector collector = new ExternMetricCollector();
    collector.handle(new Object());
  }
}
