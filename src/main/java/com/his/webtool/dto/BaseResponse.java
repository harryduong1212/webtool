package com.his.webtool.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public abstract class BaseResponse implements Serializable {

  /** serialVersionUID */
  @Serial
  private static final long serialVersionUID = 1L;
}
