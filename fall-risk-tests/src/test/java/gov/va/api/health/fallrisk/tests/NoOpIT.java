package gov.va.api.health.fallrisk.tests;

import gov.va.api.health.fallrisk.tests.categories.LabFallRisk;
import gov.va.api.health.fallrisk.tests.categories.ProdFallRisk;
import gov.va.api.health.sentinel.categories.Local;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Slf4j
public class NoOpIT {

  @Test
  @Category({Local.class, ProdFallRisk.class, LabFallRisk.class})
  public void noOperation() {
    String url = System.getProperty("integration.fallrisk.url", "Not-Found");
    String apiPath = System.getProperty("integration.fallrisk.api-path", "Not-Found");
    log.info("Integration Tests Running: {}/{}", url, apiPath);
  }
}
