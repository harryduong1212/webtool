package com.his.webtool.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.webtool.filter.JwtTokenFilter;
import com.his.webtool.filter.WebAuthenticationFilter;
import com.his.webtool.handler.WebAuthenticationFailureHandler;
import com.his.webtool.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ServerAuthenticationFailureHandler webAuthenticationFailureHandler;
    private final ReactiveUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new WebAuthenticationManager(userDetailsService, passwordEncoder());
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain apiFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/users/register").permitAll()
                        .anyExchange().authenticated())
                .addFilterAt(new JwtTokenFilter(jwtUtil), SecurityWebFiltersOrder.AUTHORIZATION)
//                .securityContextRepository(securityContextRepository)
                .build();
    }

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .httpBasic(withDefaults())
                .formLogin(formLoginSpec ->
                        formLoginSpec.authenticationFailureHandler(webAuthenticationFailureHandler)
                                .authenticationManager(authenticationManager()))
                .addFilterAt(new WebAuthenticationFilter(authenticationManager(), jwtUtil),
                        SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
