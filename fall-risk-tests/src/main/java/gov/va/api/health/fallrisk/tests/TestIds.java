package gov.va.api.health.fallrisk.tests;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/** Collection of IDs needed by the tests. */
@Value
@Builder(toBuilder = true)
public final class TestIds {
  @NonNull String patient;

  @NonNull String station;

  @NonNull String since;
}
