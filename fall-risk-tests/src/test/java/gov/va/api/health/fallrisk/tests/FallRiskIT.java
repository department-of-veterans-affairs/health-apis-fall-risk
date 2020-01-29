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
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Slf4j
public class FallRiskIT {

  private String apiPath() {
    return TestClients.fallRisk().service().apiPath();
  }

  private Header fallRiskToken() {
    return new Header("client-key", System.getProperty("fall-risk-token", "not-supplied"));
  }

  @Test
  @Category({Local.class, LabFallRisk.class, ProdFallRisk.class})
  public void searchByFacilityAndSince() {
    final String facility = systemDefinition().testIds().station();
    final String lastUpdated = systemDefinition().testIds().since();
    log.info(
        "Verify {}assessment?facility={}&since={} is list of FallRiskAssessmentResponse (200)",
        apiPath(),
        facility,
        lastUpdated);
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
                            + facility
                            + "&since="
                            + lastUpdated))
            .expect(200)
            .expectListOf(FallRiskAssessmentResponse.class);

    assertThat(response).isNotEmpty();
  }

  @Test
  @Category({Local.class, LabFallRisk.class, ProdFallRisk.class})
  public void searchByFacilityAndSinceReturnsBadRequestForUnparseableDate() {
    log.info("Verify {}assessment?facility=640&since=IDKMYBFFJILL is status (400)", apiPath());
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
    final String patientFullIcn = systemDefinition().testIds().patient();
    log.info(
        "Verify {}assessment?patient={} is list of FallRiskAssessmentResponse (200)",
        apiPath(),
        patientFullIcn);
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
                            + patientFullIcn))
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
