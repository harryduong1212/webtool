package com.his.webtool.controller;

import com.his.webtool.common.mapper.ModelConverter;
import com.his.webtool.dto.request.UserRegisterRequest;
import com.his.webtool.dto.response.UserRegisterResponse;
import com.his.webtool.service.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRegisterController extends BaseRestController {

  private final UserRegister userRegister;

  private final ModelConverter modelConverter;

  @PostMapping("/register")
  public UserRegisterResponse search(@RequestBody @Valid UserRegisterRequest request) {

    UserRegister.Input input = modelConverter.map(request, UserRegister.Input.class);

    UserRegister.Output output = userRegister.execute(input);

    return modelConverter.map(output, UserRegisterResponse.class);
  }
}
