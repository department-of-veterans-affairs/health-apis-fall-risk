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
  @Category({Local.class, LabFallRisk.class})
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
                            + "assessment?facility=640&since=1200000000"))
            .expect(200)
            .expectListOf(FallRiskAssessmentResponse.class);
    assertThat(response.size()).isEqualTo(2);
  }

  @Test
  @Category({ProdFallRisk.class})
  public void searchByFacilityAndSinceForProd() {
    ExpectedResponse.of(
            TestClients.fallRisk()
                .service()
                .requestSpecification()
                .header(fallRiskToken())
                .contentType("application/json")
                .request(
                    Method.GET,
                    TestClients.fallRisk().service().urlWithApiPath()
                        + "assessment?facility=640&since=1200000000"))
        .expect(500);
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
  @Category({Local.class, LabFallRisk.class})
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
                            + "assessment?patient=43000199"))
            .expect(200)
            .expectListOf(FallRiskAssessmentResponse.class);
    assertThat(response.size()).isEqualTo(1);
    assertThat(response.get(0).facilityId()).isEqualTo("640");
    assertThat(response.get(0).patient()).isEqualTo("43000199");
  }

  @Test
  @Category({ProdFallRisk.class})
  public void searchByPatientForProd() {
    ExpectedResponse.of(
            TestClients.fallRisk()
                .service()
                .requestSpecification()
                .header(fallRiskToken())
                .contentType("application/json")
                .request(
                    Method.GET,
                    TestClients.fallRisk().service().urlWithApiPath()
                        + "assessment?patient=43000199"))
        .expect(500);
  }
}
