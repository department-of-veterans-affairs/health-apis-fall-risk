package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.logging.Loggable;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Loggable
public interface SurveyRepository extends PagingAndSortingRepository<SurveyEntity, String> {

  String SURVEY_BY_FACILITY_AND_TIME =
      "select s from SurveyEntity s where s.sta3n = ?1"
          + " and s.surveySavedDateTime > ?2"
          + " and s.surveyName = ?3";

  @Query(SURVEY_BY_FACILITY_AND_TIME)
  List<SurveyEntity> findByFacilityIdTimeAndSurveyName(int facility, long since, String surveyName);

  SurveyEntity findByPatientFullIcnAndSurveyName(String icn, String surveyName);
}
