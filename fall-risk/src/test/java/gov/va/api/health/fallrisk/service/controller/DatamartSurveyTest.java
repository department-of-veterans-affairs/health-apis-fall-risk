package gov.va.api.health.fallrisk.service.controller;

import static gov.va.api.health.autoconfig.configuration.JacksonConfig.createMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Test;

public class DatamartSurveyTest {
  public void assertReadable(String json) throws java.io.IOException {
    DatamartSurvey dm =
        createMapper().readValue(getClass().getResourceAsStream(json), DatamartSurvey.class);
    assertThat(dm).isEqualTo(sample());
  }

  private DatamartSurvey sample() {
    return DatamartSurvey.builder()
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
  }

  @Test
  @SneakyThrows
  public void unmarshalSample() {
    assertReadable("datamart-survey.json");
  }
}
