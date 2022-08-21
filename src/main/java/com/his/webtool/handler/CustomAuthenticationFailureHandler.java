/**
 * Wincal-X project
 * CustomAuthenticationFailureHandler.java
 * Copyright © ALMEX Inc. All rights reserved.
 */
package com.his.webtool.handler;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.webtool.common.JsonResponseBuilder;
import lombok.RequiredArgsConstructor;

/**
 * FirebaseAuthenticationFailureHandler
 * to handle return response for authentication failure
 * implements AuthenticationFailureHandler to override onAuthenticationFailure
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  /**
   * handle custom response on authentication failure
   * Reponse body
   * content-type "application/json"
   * content code, message, exception, trace
   * 
   * @param request the request
   * @param response the response
   * @param exception exception from authentication process
   * @throws IOException
   */
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> errors = new LinkedHashMap<>();
    errors.put("code", response.getStatus());
    errors.put("message", exception.getMessage());
    errors.put("trace",
        exception.getStackTrace() != null && exception.getStackTrace().length > 0
            ? exception.getStackTrace()[0]
            : null);
    String responseString = objectMapper.writeValueAsString(
        JsonResponseBuilder.create(HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            request.getRequestURI(),
            null,
            errors));

    ServletOutputStream out = response.getOutputStream();

    out.write(responseString.getBytes("UTF-8"));
    out.flush();
    out.close();
  }
}
