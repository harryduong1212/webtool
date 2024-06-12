package com.his.webtool.service.template;

import reactor.core.publisher.Mono;

public interface IService<I, O> {
  /**
   * Service execute
   *
   * @param input {@link I}
   * @return {@link Mono<O>}
   */
  Mono<O> execute(I input);

}
