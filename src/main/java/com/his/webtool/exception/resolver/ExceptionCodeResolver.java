package com.his.webtool.exception.resolver;

/**
 * Determines exception code
 */
public interface ExceptionCodeResolver {

  /**
   * Determines exception code.
   * 
   * @param ex {@link Exception}
   * @return Exception code based on Exception object
   */
  String resolveExceptionCode(Exception ex);

  /**
   * Determines exception code.
   *
   * @param defaultExceptionCode exception code
   * @return Exception code based on Exception object
   */
  String resolveExceptionCode(String defaultExceptionCode);
}
