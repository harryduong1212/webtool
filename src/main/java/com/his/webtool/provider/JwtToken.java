package com.his.webtool.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class JwtToken extends UsernamePasswordAuthenticationToken {

  private final String token;

  public JwtToken(final String token) {
    super(null, null);
    this.token = token;
  }
}
