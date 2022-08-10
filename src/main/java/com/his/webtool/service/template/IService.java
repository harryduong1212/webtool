package com.his.webtool.service.template;

public interface IService<I, O> {
  /**
   * Service execute
   *
   * @param input {@link I}
   * @return {@link O}
   */
  O execute(I input);
}
