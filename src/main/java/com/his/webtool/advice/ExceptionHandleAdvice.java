package com.his.webtool.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.his.webtool.common.JsonResponseBuilder;
import com.his.webtool.common.constant.CommonError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.his.webtool.builder.ApiErrorBuilder;
import com.his.webtool.common.ApiError;
import com.his.webtool.exception.InvalidRequestException;
import com.his.webtool.exception.ResourceNotFoundException;
import com.his.webtool.exception.ResultMessagesNotificationException;
import com.his.webtool.exception.resolver.ExceptionCodeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandleAdvice extends ResponseEntityExceptionHandler {

  private final ApiErrorBuilder apiErrorBuilder;

  private final ExceptionCodeResolver exceptionCodeResolver;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return handleBindingResult(ex, ex.getBindingResult(), headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    if (ex.getCause() instanceof Exception) {
      return handleNotReadableSpecificExceptions(ex, headers, status, request);
    } else {
      return handleExceptionInternal(ex, null, headers, status, request);
    }
  }

  private ResponseEntity<Object> handleBindingResult(Exception ex, BindingResult bindingResult,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    String errorCode = exceptionCodeResolver.resolveExceptionCode(ex);
    ApiError apiError = apiErrorBuilder.createBindingResultApiError(errorCode,
        bindingResult,
        ex.getMessage());
    return handleExceptionInternal(ex, apiError, headers, status, request);
  }

  private ResponseEntity<Object> handleResultMessagesNotificationException(
      ResultMessagesNotificationException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String errorCode = exceptionCodeResolver.resolveExceptionCode(ex);
    ApiError apiError = apiErrorBuilder.createResultMessagesApiError(errorCode,
        ex.getResultMessages(),
        ex.getMessage());
    return handleExceptionInternal(ex, apiError, headers, status, request);
  }

  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex,
      WebRequest request) {
    return handleResultMessagesNotificationException(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
      WebRequest request) {
    return handleResultMessagesNotificationException(ex, new HttpHeaders(), HttpStatus.NOT_FOUND,
        request);
  }

  private ResponseEntity<Object> handleNotReadableSpecificExceptions(Exception ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    Object clazz = ex.getCause().getCause();
    if (Objects.isNull(clazz)) {
      return handleExceptionInternal((Exception) ex.getCause(), null, headers, status, request);
    }
    if (clazz instanceof InvalidRequestException) {
      return handleInvalidRequestException((InvalidRequestException) clazz, request);
    }
    return handleExceptionInternal((Exception) ex.getCause(), null, headers, status, request);
  }

  /**
   * Generate response body if throwing exception
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    log.error("handleExceptionInternal :" + ex.getMessage(), ex);
    super.handleExceptionInternal(ex, body, headers, status, request);

    Object apiError;
    if (body == null) {
      String errorCode = exceptionCodeResolver.resolveExceptionCode(ex);
      apiError = apiErrorBuilder.createApiError(errorCode, ex);
    } else {
      apiError = body;
      if (body instanceof ApiError) {
        apiError = body;
      }
    }

    // Handle InvalidFormatException in Jackson
    if (ex instanceof InvalidFormatException) {
      apiError = apiErrorBuilder.createApiError(CommonError.CR1016, "");
    }

    return ResponseEntity.status(status).headers(headers)
        .body(JsonResponseBuilder.create(status.value(),
            status.getReasonPhrase(), ((ServletWebRequest) request).getRequest().getRequestURI(),
            null, apiError));
  }
}
