package com.his.webtool.service;

import com.his.webtool.service.template.IService;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;

public interface UserRegister extends IService<UserRegister.Input, UserRegister.Output> {

  @Data
  class Input {
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private HashMap<String, Object> address;
    private String phoneNumber;
    private String email;
    private String roleCode;
  }

  @Data
  class Output {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private HashMap<String, Object> address;
    private String phoneNumber;
    private String email;
    private String roleCode;
  }
}
