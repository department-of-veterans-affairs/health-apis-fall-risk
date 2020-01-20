package gov.va.api.health.fallrisk.tests;

import static org.assertj.core.api.Assertions.assertThat;

import gov.va.api.health.fallrisk.service.controller.FallRiskAssessmentResponse;
import gov.va.api.health.fallrisk.tests.categories.LabFallRisk;
import gov.va.api.health.fallrisk.tests.categories.ProdFallRisk;
import gov.va.api.health.sentinel.ExpectedResponse;
import gov.va.api.health.sentinel.categories.Local;
import io.restassured.http.Header;
import io.restassured.http.Method;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class FallRiskIT {

  private Header fallRiskToken() {
    return new Header("client-key", System.getProperty("fall-risk-token", "not-supplied"));
  }

  @Test
  @Category({Local.class, LabFallRisk.class, ProdFallRisk.class})
  public void searchByFacilityAndSince() {
    List<FallRiskAssessmentResponse> response =
        ExpectedResponse.of(
                TestClients.fallRisk()
                    .service()
                    .requestSpecification()
                    .header(fallRiskToken())
                    .contentType("application/json")
                    .request(
                        Method.GET,
                        TestClients.fallRisk().service().urlWithApiPath()
                            + "assessment?facility="
                            + systemDefinition().testIds().station()
                            + "&since="
                            + systemDefinition().testIds().since()))
            .expect(200)
            .expectListOf(FallRiskAssessmentResponse.class);
    assertThat(response).isNotEmpty();
  }

  @Test
  @Category({Local.class, LabFallRisk.class, ProdFallRisk.class})
  public void searchByFacilityAndSinceReturnsBadRequestForUnparseableDate() {
    ExpectedResponse.of(
            TestClients.fallRisk()
                .service()
                .requestSpecification()
                .header(fallRiskToken())
                .contentType("application/json")
                .request(
                    Method.GET,
                    TestClients.fallRisk().service().urlWithApiPath()
                        + "assessment?facility=640&since=IDKMYBFFJILL"))
        .expect(400);
  }

  @Test
  @Category({Local.class, LabFallRisk.class, ProdFallRisk.class})
  public void searchByPatient() {
    List<FallRiskAssessmentResponse> response =
        ExpectedResponse.of(
                TestClients.fallRisk()
                    .service()
                    .requestSpecification()
                    .header(fallRiskToken())
                    .contentType("application/json")
                    .request(
                        Method.GET,
                        TestClients.fallRisk().service().urlWithApiPath()
                            + "assessment?patient="
                            + systemDefinition().testIds().patient()))
            .expect(200)
            .expectListOf(FallRiskAssessmentResponse.class);
    assertThat(response).isNotEmpty();
    assertThat(response.get(0).facilityId()).isEqualTo(systemDefinition().testIds().station());
    assertThat(response.get(0).patient()).isEqualTo(systemDefinition().testIds().patient());
  }

  private SystemDefinition systemDefinition() {
    return SystemDefinitions.systemDefinition();
  }
}
