package com.his.webtool.dto.response;

import com.his.webtool.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterResponse extends BaseResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate dayOfBirth;
  private HashMap<String, Object> address;
  private String phoneNumber;
  private String email;
  private String roleCode;
}
