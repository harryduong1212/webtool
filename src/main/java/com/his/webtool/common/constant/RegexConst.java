package com.his.webtool.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexConst {

  public static final String UUID =
      "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
  public static final String PHONE_NUMBER = "^((\\+)84|0)[1-9](\\d{2}){4}$";
  public static final String EMAIL_SIMPLE = "^(.+)@(\\S+)$";
}
