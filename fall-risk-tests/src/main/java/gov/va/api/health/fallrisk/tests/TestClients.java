package gov.va.api.health.fallrisk.tests;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import gov.va.api.health.sentinel.BasicTestClient;
import gov.va.api.health.sentinel.TestClient;
import lombok.experimental.UtilityClass;

/**
 * Test clients for interacting with different services (bulk-fhir, data-query, fall-risk) in a
 * {@link SystemDefinition}.
 */
@UtilityClass
public class TestClients {

  static TestClient fallRisk() {
    return BasicTestClient.builder()
        .service(SystemDefinitions.systemDefinition().getFallRisk())
        .contentType("application/json")
        .mapper(JacksonConfig::createMapper)
        .build();
  }
}
