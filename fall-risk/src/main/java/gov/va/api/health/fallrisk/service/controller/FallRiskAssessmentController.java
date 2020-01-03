package gov.va.api.health.fallrisk.service.controller;

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
  value = {"/assessment"},
  produces = {"application/json"}
)
@Slf4j
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FallRiskAssessmentController {

  private static String FALL_RISK_SURVEY_NAME = "MORSE FALL SCALE";

  private SurveyRepository surveyRepository;

  /**
   * Search for the fall risk responses for a facility after a given time.
   *
   * @param facilityId The facility to search for fall risk surveys for.
   * @param since The modified time to search from
   * @return The fall risk responses for the Facility and time.
   */
  @GetMapping(params = {"facility", "since"})
  public List<FallRiskAssessmentResponse> searchByFacilityAndSince(
      @RequestParam("facility") int facilityId, @RequestParam("since") long since) {
    return surveyRepository
        .findByFacilityIdTimeAndSurveyName(facilityId, since, FALL_RISK_SURVEY_NAME)
        .stream()
        .map(SurveyEntity::asDatamartSurvey)
        .map(DatamartSurvey::asFallRiskAssessmentResponse)
        .collect(Collectors.toList());
  }

  /**
   * Search for the fall risk response for a given patient.
   *
   * @param patientIcn The patient to search for fall risk surveys for.
   * @return The fall risk response for the patient.
   */
  @GetMapping(params = {"patient"})
  public FallRiskAssessmentResponse searchByPatient(@RequestParam("patient") String patientIcn) {
    return surveyRepository
        .findByPatientFullIcnAndSurveyName(patientIcn, FALL_RISK_SURVEY_NAME)
        .asDatamartSurvey()
        .asFallRiskAssessmentResponse();
  }
}
