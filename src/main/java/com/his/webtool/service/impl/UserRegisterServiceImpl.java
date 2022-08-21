package com.his.webtool.service.impl;

import com.his.webtool.common.constant.CommonError;
import com.his.webtool.common.mapper.ModelConverter;
import com.his.webtool.entity.Role;
import com.his.webtool.entity.Users;
import com.his.webtool.exception.InvalidRequestException;
import com.his.webtool.exception.ResourceNotFoundException;
import com.his.webtool.repository.UserRepository;
import com.his.webtool.service.UserRegister;
import com.his.webtool.service.template.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl extends BaseService<UserRegister.Input, UserRegister.Output>
    implements UserRegister{

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  protected void preExecute(Input input) {
    // do nothing
  }

  @Override
  protected Output doExecute(Input input) {

    if (userRepository.findByUsername(input.getUsername()).isPresent()) {
      // TODO: must change to valid exception
//      throw new InvalidRequestException(CommonError.CR1025);
    }

    Users newUser = new ModelConverter().map(input, Users.class)
      .setPassword(passwordEncoder.encode(input.getPassword()));
    userRepository.save(newUser);

    return new ModelConverter().map(newUser, Output.class);
  }

  @Override
  protected void postExecute(Input input) {
    // do nothing
  }
}
