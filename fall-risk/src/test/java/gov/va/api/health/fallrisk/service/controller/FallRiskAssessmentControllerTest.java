package gov.va.api.health.fallrisk.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FallRiskAssessmentControllerTest {

  @Mock FallRiskRepository fallRiskRepository;

  FallRiskAssessmentController controller() {
    return FallRiskAssessmentController.builder().fallRiskRepository(fallRiskRepository).build();
  }

  @SneakyThrows
  private FallRiskEntity fakeSurveyEntity() {
    DatamartFallRisk survey =
        DatamartFallRisk.builder()
            .cdwId("1000000030337")
            .patientFullIcn("12345V67890")
            .morseAdmitScore(50)
            .morseCategory("medium")
            .station(640)
            .morseAdmitDateTime(Instant.parse("1997-05-09T14:21:18Z"))
            .admitSpecialty("FALL")
            .admitDateTime(Instant.parse("1994-02-11T14:21:18Z"))
            .currentWard("MERCY")
            .roomBed("RM O BED W")
            .lastFour("7676")
            .patientName("OXTON, LENA")
            .stationName(Optional.of("GIBRALTAR"))
            .build();
    return FallRiskEntity.builder()
        .cdwId("1000000030337")
        .patientFullIcn("12345V67890")
        .station(640)
        .morseScore(50)
        .morseCategory("medium")
        .surveyGivenDateTime(Instant.parse("1997-05-09T14:21:18Z"))
        .payload(JacksonConfig.createMapper().writeValueAsString(survey))
        .build();
  }

  @Test
  void searchByFacilityAndSinceAsDateReturnsCorrectFallRiskResponse() {
    FallRiskEntity fakeFallRiskEntity = fakeSurveyEntity();
    when(fallRiskRepository.findByFacilityIdAndTime(anyInt(), any(Instant.class)))
        .thenReturn(List.of(fakeFallRiskEntity));
    List<FallRiskAssessmentResponse> response =
        controller().searchByFacilityAndSince(640, Instant.now().toString());
    assertThat(response)
        .containsExactly(
            FallRiskAssessmentResponse.builder()
                .patient("12345V67890")
                .facilityId("640")
                .morseScore(50)
                .timeModified(Instant.parse("1997-05-09T14:21:18Z"))
                .build());
  }

  @Test
  void searchByFacilityAndSinceAsLongReturnsCorrectFallRiskResponse() {
    FallRiskEntity fakeFallRiskEntity = fakeSurveyEntity();
    when(fallRiskRepository.findByFacilityIdAndTime(anyInt(), any(Instant.class)))
        .thenReturn(List.of(fakeFallRiskEntity));
    List<FallRiskAssessmentResponse> response =
        controller().searchByFacilityAndSince(640, Long.toString(Instant.now().toEpochMilli()));
    assertThat(response)
        .containsExactly(
            FallRiskAssessmentResponse.builder()
                .patient("12345V67890")
                .facilityId("640")
                .morseScore(50)
                .timeModified(Instant.parse("1997-05-09T14:21:18Z"))
                .build());
  }

  @Test
  void searchByPatientReturnsCorrectFallRiskResponse() {
    FallRiskEntity fallRiskEntity = fakeSurveyEntity();
    when(fallRiskRepository.findByPatientFullIcn(anyString())).thenReturn(List.of(fallRiskEntity));
    List<FallRiskAssessmentResponse> response = controller().searchByPatient("12345V67890");
    assertThat(response)
        .containsExactly(
            FallRiskAssessmentResponse.builder()
                .patient("12345V67890")
                .facilityId("640")
                .morseScore(50)
                .timeModified(Instant.parse("1997-05-09T14:21:18Z"))
                .build());
    assertThat(fallRiskEntity.toString()).isNotNull();
  }
}
