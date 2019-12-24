package gov.va.api.health.fallrisk.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import java.time.Instant;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FallRiskControllerTest {

  @Mock SurveyRepository surveyRepository;

  FallRiskController controller() {
    return FallRiskController.builder().surveyRepository(surveyRepository).build();
  }

  @Test
  @SneakyThrows
  void searchByPatientReturnsCorrectFallRiskResponse() {
    DatamartSurvey survey =
        DatamartSurvey.builder()
            .cdwId("1000000030337")
            .isCompleteFlag(false)
            .patientSid(1234)
            .locationSid(123455)
            .patientFullIcn("12345V67890")
            .rawScore(50)
            .sta3n(640)
            .surveyGivenDateTime(Instant.parse("1997-05-09T14:21:18Z"))
            .surveyResultSid(55555)
            .surveySavedDateTime(Instant.parse("1997-05-09T14:21:18Z"))
            .surveyName("FAKE SURVEY")
            .testPatientFlag(false)
            .surveyScale("FAKE SCALE")
            .transmissionTime(Instant.parse("1997-05-09T14:21:18Z"))
            .transmissionStatus("Y")
            .administeredBy(
                DatamartSurvey.Provider.builder()
                    .emailAddress("drwinston@ow.com")
                    .firstName("Harold")
                    .lastName("Winston")
                    .name("Winston, Harold")
                    .networkUsername("ilovepeanutbutter")
                    .staffSid(678)
                    .build())
            .orderedBy(
                DatamartSurvey.Provider.builder()
                    .staffSid(777)
                    .networkUsername("heroesneverdie")
                    .firstName("Angela")
                    .lastName("Ziegler")
                    .name("Ziegler, Angela")
                    .emailAddress("mercy@ow.com")
                    .build())
            .surveyQuestion(
                List.of(
                    DatamartSurvey.SurveyQuestion.builder()
                        .surveyQuestionText(
                            "Are you experiencing any problems with your genetic therapy?")
                        .surveyChoiceText("A crippling addiction to peanut butter.")
                        .legacyValue("Y")
                        .questionSequence(10)
                        .surveyAnswerText("A crippling addiction to peanut butter.")
                        .build()))
            .build();
    when(surveyRepository.findByPatientFullIcnAndSurveyName(anyString(), anyString()))
        .thenReturn(
            SurveyEntity.builder()
                .cdwId("1000000030337")
                .patientFullIcn("12345V67890")
                .sta3n(640)
                .surveyName("FAKE SURVEY")
                .payload(JacksonConfig.createMapper().writeValueAsString(survey))
                .build());

    FallRiskResponse response = controller().searchByPatient("12345V67890");
    assertThat(response)
        .isEqualTo(
            FallRiskResponse.builder()
                .patient("12345V67890")
                .facilityId("640")
                .morseScore(50)
                .providerEmail("mercy@ow.com")
                .timeModified(Instant.parse("1997-05-09T14:21:18Z"))
                .build());
  }
}
