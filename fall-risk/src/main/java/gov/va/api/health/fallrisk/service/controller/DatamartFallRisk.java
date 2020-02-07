package gov.va.api.health.fallrisk.service.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.time.Instant;
import java.util.Optional;
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

  Instant admitDateTime;

  String admitSpecialty;

  String attendingProvider;

  String cdwId;

  String currentWard;

  String lastFour;

  Instant morseAdmitDateTime;

  int morseAdmitScore;

  String morseCategory;

  String patientFullIcn;

  String patientName;

  String roomBed;

  int station;

  Optional<String> stationName;

  @Builder.Default private String objectType = "Fall Risk";

  @Builder.Default private String objectVersion = "1";

  /**
   * Convert this survey to a FallRiskResponse.
   *
   * @return The FallRiskResponse
   */
  FallRiskAssessmentResponse asFallRiskAssessmentResponse() {
    return FallRiskAssessmentResponse.builder()
        .patient(patientFullIcn)
        .facilityId(Integer.toString(station))
        .morseScore(morseAdmitScore)
        .timeModified(morseAdmitDateTime)
        .admitDateTime(admitDateTime)
        .admitSpecialty(admitSpecialty)
        .attendingProvider(attendingProvider)
        .currentWard(currentWard)
        .lastFour(lastFour)
        .morseCategory(morseCategory)
        .patientName(patientName)
        .roomBed(roomBed)
        .stationName(stationName.get())
        .build();
  }
}
