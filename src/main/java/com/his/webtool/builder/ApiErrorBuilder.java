package com.his.webtool.builder;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import com.his.webtool.common.ApiError;
import com.his.webtool.exception.resolver.ExceptionCodeResolver;
import com.his.webtool.message.ResultMessage;
import com.his.webtool.message.ResultMessages;

@Component
public class ApiErrorBuilder {

  @Autowired
  MessageSource messageSource;

  @Autowired
  ExceptionCodeResolver exceptionCodeResolver;

  private ApiError createApiError(DefaultMessageSourceResolvable messageResolvable, String target) {
    String localizedMessage = messageSource.getMessage(messageResolvable,  LocaleContextHolder.getLocale());
    return new ApiError(messageResolvable.getCode(), localizedMessage, target);
  }

  private ApiError createApiErrorFieldValidation(DefaultMessageSourceResolvable messageResolvable,
      String target) {
    String localizedMessage = messageSource.getMessage(messageResolvable, LocaleContextHolder.getLocale());
    String fieldErrorCode = exceptionCodeResolver.resolveExceptionCode(messageResolvable.getCode());
    return new ApiError(fieldErrorCode, localizedMessage, target);
  }

  public ApiError createApiError(String errorCode, String defaultErrorMessage,
      Object... arguments) {
    String localizedMessage = messageSource.getMessage(errorCode, arguments, defaultErrorMessage, LocaleContextHolder.getLocale());
    return new ApiError(errorCode, localizedMessage);
  }

  public ApiError createApiError(String errorCode, Exception ex, Object... arguments) {
    String localizedMessage = messageSource.getMessage(errorCode, arguments, ex.getLocalizedMessage(),
        LocaleContextHolder.getLocale());
    ApiError apiError = new ApiError(errorCode,
        ex.getCause() == null ? localizedMessage : ex.getCause().getLocalizedMessage(), null, null);
    apiError.addDetail(
        new ApiError(errorCode, ex.getCause() == null ? localizedMessage : ex.getCause().getLocalizedMessage(), null,
            ex.getStackTrace().length > 0 ? ex.getStackTrace()[0] : null));
    return apiError;
  }

  public ApiError createBindingResultApiError(String errorCode, BindingResult bindingResult,
      String defaultErrorMessage) {
    ApiError apiError = createApiError(errorCode, defaultErrorMessage);
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      fieldError.unwrap(ConstraintViolationImpl.class);
      apiError.addDetail(createApiErrorFieldValidation(fieldError, fieldError.getField()));
    }
    for (ObjectError objectError : bindingResult.getGlobalErrors()) {
      apiError.addDetail(createApiError(objectError, objectError.getObjectName()));
    }
    return apiError;
  }

  public ApiError createResultMessagesApiError(String rootErrorCode, ResultMessages resultMessages,
      String defaultErrorMessage) {
    ApiError apiError;
    if (resultMessages.getList().size() == 1) {
      ResultMessage resultMessage = resultMessages.iterator().next();
      String errorCode = resultMessage.getCode();
      String errorText = resultMessage.getText();
      if (errorCode == null && errorText == null) {
        apiError = createApiError(null, (String) null, resultMessage.getArgs());
      } else {
        apiError = createApiError(rootErrorCode, defaultErrorMessage);
        apiError.addDetail(createApiError(errorCode, errorText, resultMessage.getArgs()));
      }

    } else {
      apiError = createApiError(rootErrorCode, defaultErrorMessage);
      for (ResultMessage resultMessage : resultMessages.getList()) {
        apiError.addDetail(
            createApiError(resultMessage.getCode(), resultMessage.getText(), null, resultMessage.getArgs()));
      }
    }
    return apiError;
  }
}
