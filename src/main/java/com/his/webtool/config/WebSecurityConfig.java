package com.his.webtool.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.his.webtool.filter.JwtTokenFilter;
import com.his.webtool.handler.CustomAuthenticationFailureHandler;
import com.his.webtool.provider.UserDetailsAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

//  private final UserDetailsService userDetailsService;

  private final ObjectMapper objectMapper;

  private final UserDetailsAuthenticationProvider authenticationProvider;

  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  private final String tokenHeader = "x-auth";

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(Collections.singletonList(authenticationProvider));
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // disable CORS and CSRF
    http.cors().and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest().authenticated();

    http.addFilterBefore(authenticationTokenFilter(tokenHeader),
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // permit all user login and register request
    return (web) -> web.ignoring().antMatchers("/users/login", "/users/register");
  }

  /**
   * To addFilterBefore for HttpSecurity
   *
   * @param tokenHeader token header
   * @return FirebaseAuthenticationTokenFilter
   * @throws Exception
   */
  public JwtTokenFilter authenticationTokenFilter(String tokenHeader)
      throws Exception {
    JwtTokenFilter authenticationTokenFilter = new JwtTokenFilter(tokenHeader, objectMapper);

    authenticationTokenFilter.setAuthenticationManager(authenticationManager());
    authenticationTokenFilter.setAuthenticationSuccessHandler(
        (request, response, authentication) -> {});
    authenticationTokenFilter.setSessionAuthenticationStrategy(
        new NullAuthenticatedSessionStrategy());
    authenticationTokenFilter.setAllowSessionCreation(false);
    authenticationTokenFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

    return authenticationTokenFilter;
  }
}
