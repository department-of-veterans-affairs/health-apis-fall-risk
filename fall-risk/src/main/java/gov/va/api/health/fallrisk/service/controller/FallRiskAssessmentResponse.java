package gov.va.api.health.fallrisk.service.controller;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FallRiskAssessmentResponse {
  @NotNull String patient;

  @NotNull int morseScore;

  @NotNull String facilityId;

  @NotNull Instant timeModified;

  @NotNull String providerEmail;
}
