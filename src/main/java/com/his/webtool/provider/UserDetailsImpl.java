package com.his.webtool.provider;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.his.webtool.dto.LoginUser;
import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;

  private final boolean enabled = true;
  private final boolean credentialsNonExpired = true;
  private final boolean accountNonLocked = true;
  private final boolean accountNonExpired = true;
  private final String password = null;
  private final String username;
  private final Long id;
  private List<String> authorities;
  private LoginUser loginUser;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    /**
     * Convert authorities text into SimpleGrantedAuthority
     */
    return authorities.parallelStream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

  public UserDetailsImpl(LoginUser loginUser, List<String> authorities) {
    this.username = loginUser.getUsername();
    this.id = loginUser.getId();
    this.authorities = authorities;
    this.loginUser = loginUser;
  }

  public UserDetailsImpl(String username, Long id) {
    this.username = username;
    this.id = id;
  }
}
