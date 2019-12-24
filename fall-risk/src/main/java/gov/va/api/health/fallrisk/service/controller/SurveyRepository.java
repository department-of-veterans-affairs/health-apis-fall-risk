package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.logging.Loggable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Loggable
public interface SurveyRepository extends PagingAndSortingRepository<SurveyEntity, String> {
  SurveyEntity findByPatientFullIcnAndSurveyName(String icn, String surveyName);
}
