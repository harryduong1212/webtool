package com.his.webtool.config;

import com.his.webtool.dto.LoginUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class WebAuthenticationManager implements ReactiveAuthenticationManager {

    private final ReactiveUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(password, userDetails.getPassword())) {
                        final UserDetailPrinciple userDetailPrinciple = (UserDetailPrinciple) userDetails;
                        final LoginUser loginUser = LoginUser.of(userDetailPrinciple.getUserId(),
                                userDetailPrinciple.getUsername(), userDetailPrinciple.getRoleCode());
                        return Mono.just(new UsernamePasswordAuthenticationToken(loginUser, null,
                                userDetailPrinciple.getAuthorities()));
                    }

                    return Mono.error(new BadCredentialsException("Invalid credentials"));
                });
    }
}