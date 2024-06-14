package com.his.webtool.provider;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@EqualsAndHashCode(callSuper = true)
public class JwtToken extends UsernamePasswordAuthenticationToken {

    private final String token;

    public JwtToken(final String token) {
        super(null, null);
        this.token = token;
    }
}
