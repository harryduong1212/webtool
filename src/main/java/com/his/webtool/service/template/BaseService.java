package com.his.webtool.service.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public abstract class BaseService<I, O> implements IService<I, O> {
  /**
   * Execute the service process
   * <ul>
   * <li>perform pre-execute abstract process
   * <li>perform do-execute abstract process
   * <li>perforn post-execute abstract process</li>
   * </ul>
   */
  @Override
  public O execute(I input) {
    try {
      /* Perform pre-execute */
      preExecute(input);
      /* Perform do-execute */
      return doExecute(input);
    } catch (Exception e) {
      throw e;
    } finally {
      /* Perform post-execute */
      postExecute(input);
    }
  }

  /**
   * Handle validation... and all the process before doing the main process of the
   * service.
   *
   * @param input {@link I}
   */
  protected abstract void preExecute(I input);

  /**
   * Process business
   *
   * @param input {@link I}
   * @return {@link O}
   */
  protected abstract O doExecute(I input);

  /**
   * Clean all unnecessary object before the process is done
   *
   * @param input {@link I}
   */
  protected abstract void postExecute(I input);
}
