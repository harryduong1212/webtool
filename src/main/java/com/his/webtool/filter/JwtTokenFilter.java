package com.his.webtool.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.webtool.common.ApiError;
import com.his.webtool.common.JsonResponseBuilder;
import com.his.webtool.common.constant.CommonError;
import com.his.webtool.exception.InvalidRequestException;
import com.his.webtool.provider.JwtToken;
import com.his.webtool.wrapper.BaseRequestHttpRequestWrapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

  public final String tokenHeader;
  private final ObjectMapper objectMapper;

  public JwtTokenFilter(String tokenHeader, ObjectMapper objectMapper) {
    super("/**");
    this.tokenHeader = tokenHeader;
    this.objectMapper = objectMapper;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    final HttpServletRequest request = (HttpServletRequest) req;
    try {
      if (request.getContentType().contains("application/json")) {
        // For application/json
        final BaseRequestHttpRequestWrapper wrappedRequest =
            new BaseRequestHttpRequestWrapper(request);
        super.doFilter(wrappedRequest, res, chain);

      } else {
        // For different content type
        super.doFilter(request, res, chain);
      }
    } catch (InvalidRequestException e) {
      HttpServletResponse hsr = (HttpServletResponse) res;
      final var apiError =
          new ApiError(CommonError.CR1016, HttpStatus.BAD_REQUEST.getReasonPhrase());
      final var bodyResponse =
          JsonResponseBuilder.create(HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(), request.getRequestURI(), null, apiError);
      PrintWriter printWriter = hsr.getWriter();
      hsr.setContentType("application/json");
      hsr.setCharacterEncoding("UTF-8");
      hsr.setStatus(HttpStatus.BAD_REQUEST.value());
      printWriter.print(objectMapper.writeValueAsString(bodyResponse));
      printWriter.flush();
    }
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    final String authToken = request.getHeader(tokenHeader);

    if (StringUtils.isBlank(authToken)) {
      throw new AuthenticationServiceException("Invalid Authentication Header.");
    }

    return getAuthenticationManager()
        .authenticate(new JwtToken(authToken));
  }

  /**
   * Handle authentication success
   *
   * @param request {@link HttpServletRequest}
   * @param response {@link HttpServletResponse}
   * @param chain {@link FilterChain}
   * @param authResult {@link Authentication}
   * @throws IOException exception
   * @throws ServletException exception
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }
}
