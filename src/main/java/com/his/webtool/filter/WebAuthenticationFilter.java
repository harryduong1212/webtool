package com.his.webtool.filter;

import com.his.webtool.dto.LoginUser;
import com.his.webtool.util.JwtUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class WebAuthenticationFilter extends AuthenticationWebFilter {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * All-args constructor
     *
     * @param authenticationManager {@link ReactiveAuthenticationManager}
     * @param jwtUtil               {@link JwtUtil}
     */
    public WebAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return exchange.getFormData()
                .flatMap(formData -> {
                    String username = formData.getFirst("username");
                    String password = formData.getFirst("password");
                    return StringUtils.isBlank(username)
                            ? Mono.empty()
                            : Mono.just(new UsernamePasswordAuthenticationToken(username, password));
                })
                .flatMap(authenticationManager::authenticate)
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .flatMap(authentication -> onAuthenticationSuccess(authentication,
                        new WebFilterExchange(exchange, chain)));
    }

    @Override
    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

        final LoginUser principle = (LoginUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(principle, authentication.getAuthorities());

        // Set the token in the Authorization header
        response.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return Mono.empty(); // Signal successful completion
    }

    @Override
    public void setAuthenticationFailureHandler(ServerAuthenticationFailureHandler authenticationFailureHandler) {
        super.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

}
