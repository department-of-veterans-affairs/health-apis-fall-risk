package gov.va.api.health.fallrisk.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Exceptions that escape the rest controllers will be processed by this handler. It will convert
 * exception into different HTTP status codes and produce an error response payload.
 */
@RestControllerAdvice
@RequestMapping(produces = {"application/json"})
public class WebExceptionHandler {

  @ExceptionHandler({
    BindException.class,
    ConstraintViolationException.class,
    MethodArgumentNotValidException.class,
    UnsatisfiedServletRequestParameterException.class,
    NumberFormatException.class,
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleBadRequest(Exception e, HttpServletRequest request) {
    return ErrorMessage.of(e.getClass().getSimpleName() + ": " + e.getMessage());
  }

  @ExceptionHandler({HttpClientErrorException.NotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorMessage handleNotFound(Exception e, HttpServletRequest request) {
    return ErrorMessage.of(e.getMessage());
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage internalServerError(Exception e) {
    return ErrorMessage.of(e.getClass().getSimpleName() + ": " + e.getMessage());
  }

  @Value
  @AllArgsConstructor(staticName = "of")
  static class ErrorMessage {
    String message;
  }
}
