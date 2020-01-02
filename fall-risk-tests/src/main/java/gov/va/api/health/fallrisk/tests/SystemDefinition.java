package gov.va.api.health.fallrisk.tests;

import gov.va.api.health.sentinel.ServiceDefinition;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SystemDefinition {

  @NonNull ServiceDefinition fallRisk;
}
