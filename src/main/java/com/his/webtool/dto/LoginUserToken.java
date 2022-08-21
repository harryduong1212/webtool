package com.his.webtool.dto;

import java.io.Serial;
import java.io.Serializable;

import com.his.webtool.provider.JwtToken;
import com.his.webtool.util.JwtUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserToken implements LoginUser, Serializable {

  /** serialVersionUID */
  @Serial
  private static final long serialVersionUID = 1L;

  /** user_id */
  private Long id;

  /** uid */
  private String username;

  /** service_id */
  private String roleCode;
}
