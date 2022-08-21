package com.his.webtool.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}
