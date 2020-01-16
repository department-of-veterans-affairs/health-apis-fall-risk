package gov.va.api.health.fallrisk.service.controller;

import static gov.va.api.health.autoconfig.configuration.JacksonConfig.createMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Test;

public class DatamartFallRiskTest {
  public void assertReadable(String json) throws java.io.IOException {
    DatamartFallRisk dm =
        createMapper().readValue(getClass().getResourceAsStream(json), DatamartFallRisk.class);
    assertThat(dm).isEqualTo(sample());
  }

  private DatamartFallRisk sample() {
    return DatamartFallRisk.builder()
        .cdwId("1000000030337")
        .patientFullIcn("12345V67890")
        .morseScore(50)
        .morseCategory("medium")
        .station(640)
        .surveyGivenDateTimeUtc(Instant.parse("1997-05-09T14:21:18Z"))
        .surveyName("FAKE SURVEY")
        .surveyScale("FAKE SCALE")
        .bedSection("BED")
        .divisionName("OW")
        .locationName("WATCHPOINT")
        .wardLocationName("LAB")
        .specialty("FALL")
        .patientName("OXTON, LENA")
        .stationName("GIBRALTAR")
        .administeredBy(
            List.of(
                DatamartFallRisk.Provider.builder()
                    .emailAddress("drwinston@ow.com")
                    .firstName("Harold")
                    .lastName("Winston")
                    .name("Winston, Harold")
                    .npi("ilovepeanutbutter")
                    .officePhone("123")
                    .serviceSection("GENETICS")
                    .build()))
        .orderedBy(
            List.of(
                DatamartFallRisk.Provider.builder()
                    .npi("heroesneverdie")
                    .serviceSection("MEDICAL")
                    .officePhone("911")
                    .firstName("Angela")
                    .lastName("Ziegler")
                    .name("Ziegler, Angela")
                    .emailAddress("mercy@ow.com")
                    .build()))
        .build();
  }

  @Test
  @SneakyThrows
  public void unmarshalSample() {
    assertReadable("datamart-fall-risk.json");
  }
}
