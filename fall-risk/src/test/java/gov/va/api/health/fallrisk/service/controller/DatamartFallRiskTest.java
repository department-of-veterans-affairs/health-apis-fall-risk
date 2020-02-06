package gov.va.api.health.fallrisk.service.controller;

import static gov.va.api.health.autoconfig.configuration.JacksonConfig.createMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Optional;
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
        .morseAdmitScore(50)
        .attendingProvider("ZIEGLER, ANGELA")
        .morseCategory("medium")
        .station(640)
        .admitDateTime(Instant.parse("2016-05-24T14:21:18Z"))
        .currentWard("MERCY")
        .roomBed("RM O BED W")
        .lastFour("7676")
        .morseAdmitDateTime(Instant.parse("1997-05-09T14:21:18Z"))
        .admitSpecialty("FALL")
        .patientName("OXTON, LENA")
        .stationName(Optional.of("GIBRALTAR"))
        .build();
  }

  @Test
  @SneakyThrows
  public void unmarshalSample() {
    assertReadable("datamart-fall-risk.json");
  }
}
