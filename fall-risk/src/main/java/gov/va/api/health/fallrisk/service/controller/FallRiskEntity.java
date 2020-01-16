package gov.va.api.health.fallrisk.service.controller;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import java.time.Instant;
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
@Table(name = "FallRisk", schema = "app")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FallRiskEntity {

  @Id
  @Column(name = "CDWId")
  @EqualsAndHashCode.Include
  private String cdwId;

  @Column(name = "PatientFullICN")
  private String patientFullIcn;

  @Column(name = "Sta3n")
  private int station;

  @Column(name = "DateUTC", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private Instant surveyGivenDateTime;

  @Column(name = "MorseScore")
  private int morseScore;

  @Column(name = "MorseCategory")
  private String morseCategory;

  @Column(name = "FallRisk")
  @Basic(fetch = FetchType.EAGER)
  @Lob
  private String payload;

  @SneakyThrows
  DatamartFallRisk asDatamartFallRisk() {
    return JacksonConfig.createMapper().readValue(payload, DatamartFallRisk.class);
  }
}
