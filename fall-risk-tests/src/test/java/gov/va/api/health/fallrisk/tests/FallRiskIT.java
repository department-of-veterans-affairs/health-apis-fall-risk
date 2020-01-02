package gov.va.api.health.fallrisk.tests;

import gov.va.api.health.fallrisk.tests.categories.LabFallRisk;
import gov.va.api.health.fallrisk.tests.categories.ProdFallRisk;
import gov.va.api.health.sentinel.ExpectedResponse;
import gov.va.api.health.sentinel.categories.Local;
import io.restassured.http.Method;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FallRiskIT {

  @Test
  @Category({Local.class, ProdFallRisk.class, LabFallRisk.class})
  public void searchByPatient() {
    ExpectedResponse.of(
            TestClients.fallRisk()
                .service()
                .requestSpecification()
                .contentType("application/json")
                .request(
                    Method.GET,
                    TestClients.fallRisk().service().urlWithApiPath()
                        + "assessment?patient=12345V67890"))
        .expect(500);
  }
}
