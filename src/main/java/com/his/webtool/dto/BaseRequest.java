package com.his.webtool.dto;

import com.his.webtool.common.constant.RegexConst;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

@Data
public abstract class BaseRequest implements Serializable {

  /** serialVersionUID */
  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank
  private String language;

  @Pattern(regexp = RegexConst.UUID)
  private String sessionId;

}
