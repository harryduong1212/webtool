package com.his.webtool.service.impl;

import com.his.webtool.service.UserRegister;
import com.his.webtool.service.template.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl extends BaseService<UserRegister.Input, UserRegister.Output>
    implements UserRegister{

  @Override
  protected void preExecute(Input input) {
    // do nothing
  }

  @Override
  protected Output doExecute(Input input) {
    return new Output();
  }

  @Override
  protected void postExecute(Input input) {
    // do nothing
  }
}
