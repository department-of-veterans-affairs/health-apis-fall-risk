package gov.va.api.health.fallrisk.service.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.Instant;
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
public class DatamartFallRisk {

  String surveyName;

  int station;

  String stationName;

  String locationName;

  String wardLocationName;

  String divisionName;

  String specialty;

  String bedSection;

  @JsonAlias("patientFullICN")
  String patientFullIcn;

  String patientName;

  @JsonAlias("surveyGivenDateTimeUTC")
  Instant surveyGivenDateTimeUtc;

  int morseScore;

  String morseCategory;

  String surveyScale;

  Provider orderedBy;

  Provider administeredBy;

  @Builder.Default private String objectType = "Survey";

  @Builder.Default private String objectVersion = "1";

  private String cdwId;

  /**
   * Convert this survey to a FallRiskResponse.
   *
   * @return The FallRiskResponse
   */
  FallRiskAssessmentResponse asFallRiskAssessmentResponse() {
    return FallRiskAssessmentResponse.builder()
        .patient(patientFullIcn)
        .facilityId(Integer.toString(station))
        .morseScore(morseScore)
        .providerEmail(orderedBy.emailAddress)
        .timeModified(surveyGivenDateTimeUtc)
        .build();
  }

  @Data
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public static class Provider {
    String npi;

    String officePhone;

    String serviceSection;

    String name;

    String lastName;

    String firstName;

    String emailAddress;
  }
}
