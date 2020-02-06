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
@Table(name = "MorseFallRisk", schema = "app")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FallRiskEntity {

  @Column(name = "AdmitDateTime")
  private Instant admitDateTime;

  @Column(name = "AttendingProvider")
  private String attendingProvider;

  @Id
  @Column(name = "CDWId")
  @EqualsAndHashCode.Include
  private String cdwId;

  @Column(name = "CurrentSpecialty")
  private String currentSpecialty;

  @Column(name = "CurrentWard")
  private String currentWard;

  @Column(name = "PatientFullICN")
  private String patientFullIcn;

  @Column(name = "PatientName")
  private String patientName;

  @Column(name = "Sta3n")
  private int station;

  @Column(name = "StationName")
  private String stationName;

  @Column(name = "LastFour")
  private String lastFour;

  @Column(name = "MorseAdmitDateTime", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private Instant morseAdmitDateTime;

  @Column(name = "MorseAdmitScore")
  private int morseAdmitScore;

  @Column(name = "MorseCategory")
  private String morseCategory;

  @Column(name = "Payload")
  @Basic(fetch = FetchType.EAGER)
  @Lob
  private String payload;

  @Column(name = "RoomBed")
  private String roomBed;

  @SneakyThrows
  DatamartFallRisk asDatamartFallRisk() {
    return JacksonConfig.createMapper().readValue(payload, DatamartFallRisk.class);
  }
}
