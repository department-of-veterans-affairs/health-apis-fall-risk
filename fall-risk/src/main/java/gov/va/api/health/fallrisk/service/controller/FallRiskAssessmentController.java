package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.logging.Loggable;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(
    value = {"/assessment", "v0/assessment"},
    produces = {"application/json"})
@Slf4j
@Builder
@Loggable
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FallRiskAssessmentController {
  private FallRiskRepository fallRiskRepository;

  /**
   * Parse the string to a DateTime and return the epoch, or parse to a long if its not a date, and
   * assume that is an epoch.
   *
   * @param time The string to parse
   * @return The parsed instant value.
   */
  private Instant asInstant(String time) {
    try {
      return Instant.parse(time);
    } catch (DateTimeParseException e) {
      return Instant.ofEpochMilli(Long.parseLong(time));
    }
  }

  /**
   * Search for the fall risk responses for a facility after a given time.
   *
   * @param facilityId The facility to search for fall risk surveys for.
   * @param since The modified time to search from
   * @return The fall risk responses for the Facility and time.
   */
  @GetMapping(params = {"facility", "since"})
  public List<FallRiskAssessmentResponse> searchByFacilityAndSince(
      @RequestParam("facility") int facilityId, @RequestParam("since") String since) {
    return fallRiskRepository.findByFacilityIdAndTime(facilityId, asInstant(since)).stream()
        .map(FallRiskEntity::asDatamartFallRisk)
        .map(DatamartFallRisk::asFallRiskAssessmentResponse)
        .collect(Collectors.toList());
  }

  /**
   * Search for the fall risk response for a given patient.
   *
   * @param patientIcn The patient to search for fall risk surveys for.
   * @return The fall risk response for the patient.
   */
  @GetMapping(params = {"patient"})
  public List<FallRiskAssessmentResponse> searchByPatient(
      @RequestParam("patient") String patientIcn) {
    return fallRiskRepository.findByPatientFullIcn(patientIcn).stream()
        .map(FallRiskEntity::asDatamartFallRisk)
        .map(DatamartFallRisk::asFallRiskAssessmentResponse)
        .collect(Collectors.toList());
  }
}
