package com.his.webtool.dto.request;

import com.his.webtool.common.constant.RegexConst;
import com.his.webtool.dto.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterRequest extends BaseRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private LocalDate dayOfBirth;

  private Map<String, Object> address;

  @NotBlank
  @Pattern(regexp = RegexConst.PHONE_NUMBER)
  private String phoneNumber;

  @NotBlank
  @Pattern(regexp = RegexConst.EMAIL_SIMPLE)
  private String email;

  private String roleCode;
}
