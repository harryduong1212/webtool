package com.his.webtool.dto;

/**
 * LoginUser
 * to get necessary information for authentication
 */
public interface LoginUser {

  /**
   * @return username
   */
  String getUsername();

  /**
   * @return user id
   */
  Long getId();

  /**
   * @return serviceId
   */
  String getRoleCode();
}
