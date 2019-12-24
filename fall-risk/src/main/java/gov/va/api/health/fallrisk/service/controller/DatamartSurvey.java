package gov.va.api.health.fallrisk.service.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DatamartSurvey {

  String surveyName;

  int sta3n;

  int locationSid;

  long surveyResultSid;

  String patientFullIcn;

  long patientSid;

  boolean testPatientFlag;

  Instant surveyGivenDateTime;

  Instant surveySavedDateTime;

  boolean isCompleteFlag;

  Instant transmissionTime;

  String transmissionStatus;

  int rawScore;

  String surveyScale;

  List<SurveyQuestion> surveyQuestion;

  Provider orderedBy;

  Provider administeredBy;

  @Builder.Default private String objectType = "Survey";

  @Builder.Default private String objectVersion = "1";

  private String cdwId;

  public FallRiskResponse asFallRiskResponse() {
    return new FallRiskResponse();
  }

  @Data
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public static class SurveyQuestion {

    int questionSequence;

    String surveyQuestionText;

    String surveyChoiceText;

    String legacyValue;

    String surveyAnswerText;
  }

  @Data
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public static class Provider {

    long staffSid;

    String networkUsername;

    String name;

    String lastName;

    String firstName;

    String emailAddress;
  }
}
