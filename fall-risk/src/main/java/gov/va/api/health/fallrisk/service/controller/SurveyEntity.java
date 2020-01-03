package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@Entity
@Builder
@Table(name = "Survey", schema = "app")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class SurveyEntity {

  @Id
  @Column(name = "CDWId")
  @EqualsAndHashCode.Include
  private String cdwId;

  @Column(name = "PatientFullICN")
  private String patientFullIcn;

  @Column(name = "SurveyName")
  private String surveyName;

  @Column(name = "Sta3n")
  private int sta3n;

  @Column(name = "SurveyRecordedDate")
  private Long surveySavedDateTime;

  @Column(name = "Survey")
  @Basic(fetch = FetchType.EAGER)
  @Lob
  private String payload;

  @SneakyThrows
  DatamartSurvey asDatamartSurvey() {
    return JacksonConfig.createMapper().readValue(payload, DatamartSurvey.class);
  }
}
