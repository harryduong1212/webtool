package com.his.webtool.filter;

import com.his.webtool.util.JwtUtil;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log4j2
public class JwtTokenFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    /**
     * All-args constructor
     *
     * @param jwtUtil {@link JwtUtil}
     */
    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        final String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        final String token = bearerToken != null && bearerToken.startsWith("Bearer ")
                ? bearerToken.substring(7) // Remove "Bearer " prefix
                : StringUtils.EMPTY;

        if (StringUtils.isBlank(token)) {
            return chain.filter(exchange);
        }

        try {
            // Create and authenticate the Authentication object
            return Mono.just(jwtUtil.validateToken(token))
                    .flatMap(valid -> valid ? Mono.just(jwtUtil.getAuthentication(token)) : Mono.empty())
                    .as(this::verify)
                    .doOnSuccess(it -> log.debug("Authorization successful"))
                    .doOnError(AccessDeniedException.class,
                            (ex) -> log.debug(LogMessage.format("Authorization failed: %s", ex.getMessage())))
                    .switchIfEmpty(chain.filter(exchange));
        } catch (Exception e) {
            return Mono.error(new BadCredentialsException("Invalid token", e));
        }
    }

    Mono<Void> verify(Mono<Authentication> authentication) {
        return authentication.map(Authentication::getAuthorities)
                .filter(JwtUtil.AUTHORITIES::containsAll)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccessDeniedException("Access Denied"))))
                .flatMap((decision) -> Mono.empty());
    }
}
