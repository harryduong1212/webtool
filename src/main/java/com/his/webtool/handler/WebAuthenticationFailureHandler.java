package com.his.webtool.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.webtool.common.JsonResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpRequest request = webFilterExchange.getExchange().getRequest();
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED); // Set appropriate status code

        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("code", response.getStatusCode());
        errors.put("message", exception.getMessage());
        errors.put("trace", exception.getStackTrace() != null && exception.getStackTrace().length > 0
                ? exception.getStackTrace()[0]
                : null);
        try {
            byte[] responseInBytes = objectMapper.writeValueAsBytes(
                    JsonResponseBuilder.create(HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            String.valueOf(request.getURI()),
                            null,
                            errors));
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseInBytes)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
