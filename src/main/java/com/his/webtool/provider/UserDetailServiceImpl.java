package com.his.webtool.provider;

import static com.his.webtool.common.constant.CommonError.CR1024;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.his.webtool.dto.LoginUser;
import com.his.webtool.dto.LoginUserToken;
import com.his.webtool.entity.Authority;
import com.his.webtool.entity.Users;
import com.his.webtool.exception.ResourceNotFoundException;
import com.his.webtool.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> user = userRepository.findByUsername(username);

    if (user.isEmpty()) {
      throw new ResourceNotFoundException(CR1024, username);
    }

    LoginUser loginUser = new LoginUserToken(user.get().getId(),
        user.get().getUsername(),
        user.get().getRole().getCode());
    List<String> authorities = user.get().getRole().getAuthorities().stream()
        .map(Authority::getCode)
        .collect(Collectors.toList());

    return new UserDetailsImpl(loginUser, authorities);
  }

}
