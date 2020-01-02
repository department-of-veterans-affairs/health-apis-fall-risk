package gov.va.api.health.fallrisk.service.controller;

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
public class FallRiskController {

  private static String FALL_RISK_SURVEY_NAME = "IDK";

  private SurveyRepository surveyRepository;

  /**
   * Search for the fall risk response for a given patient.
   *
   * @param patientIcn The patient to search for fall risk surveys for.
   * @return The fall risk response for the patient.
   */
  @GetMapping(params = {"patient"})
  public FallRiskResponse searchByPatient(@RequestParam("patient") String patientIcn) {
    return surveyRepository
        .findByPatientFullIcnAndSurveyName(patientIcn, FALL_RISK_SURVEY_NAME)
        .asDatamartSurvey()
        .asFallRiskResponse();
  }
}
