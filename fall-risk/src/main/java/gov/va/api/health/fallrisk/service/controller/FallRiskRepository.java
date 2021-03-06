package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.logging.Loggable;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Loggable
public interface FallRiskRepository extends PagingAndSortingRepository<FallRiskEntity, String> {

  String FALL_RISK_BY_FACILITY_AND_TIME =
      "select f from FallRiskEntity f where f.station = ?1" + " and f.morseAdmitDateTime > ?2";

  @Query(FALL_RISK_BY_FACILITY_AND_TIME)
  List<FallRiskEntity> findByFacilityIdAndTime(int facility, Instant since);

  List<FallRiskEntity> findByPatientFullIcn(String icn);
}
