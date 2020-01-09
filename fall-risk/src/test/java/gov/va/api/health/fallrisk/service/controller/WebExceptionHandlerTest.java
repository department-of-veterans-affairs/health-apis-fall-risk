package gov.va.api.health.fallrisk.service.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class WebExceptionHandlerTest {

  @Test
  void handleBadRequest() {
    WebExceptionHandler.ErrorMessage result =
        handler().handleBadRequest(new RuntimeException("HI"), null);
    assertThat(result).isEqualTo(WebExceptionHandler.ErrorMessage.of("HI"));
  }

  @Test
  void handleNotFound() {
    WebExceptionHandler.ErrorMessage result =
        handler().handleNotFound(new RuntimeException("HI"), null);
    assertThat(result).isEqualTo(WebExceptionHandler.ErrorMessage.of("HI"));
  }

  WebExceptionHandler handler() {
    return new WebExceptionHandler();
  }

  @Test
  void internalServerError() {
    WebExceptionHandler.ErrorMessage result =
        handler().internalServerError(new RuntimeException("HI"));
    assertThat(result).isEqualTo(WebExceptionHandler.ErrorMessage.of("RuntimeException: HI"));
  }
}
