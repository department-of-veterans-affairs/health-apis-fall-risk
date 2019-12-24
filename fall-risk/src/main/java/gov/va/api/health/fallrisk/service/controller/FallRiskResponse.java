package gov.va.api.health.fallrisk.service.controller;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FallRiskResponse {
  String patient;

  int morseScore;

  String facilityId;

  Instant timeModified;

  String providerEmail;
}
