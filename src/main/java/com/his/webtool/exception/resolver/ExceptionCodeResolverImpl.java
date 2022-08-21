package com.his.webtool.exception.resolver;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.his.webtool.provider.ExceptionCodeProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "exception")
@Data
@Primary
public class ExceptionCodeResolverImpl implements ExceptionCodeResolver {

  /**
   * Mapping rules between exception code and exception class name.
   */
  private LinkedHashMap<String, String> exceptionMappings;

  /**
   * Default exception code.
   * <p>
   * Exception code used when it could not be resolved using the mapping rules.
   * </p>
   */
  private String defaultExceptionCode = "SI";

  /**
   * Resolves exception code.
   * <p>
   * Determines the exception code corresponding to specified exception. <br>
   * Returns default exception code if exception code could not be determined
   * based on rules. If default exception<br>
   * code is also not set, then returns {@code null}
   * </p>
   *
   * @param ex
   *             Exception
   * @return Corresponding exception code.
   */
  @Override
  public String resolveExceptionCode(Exception ex) {
    if (ex == null) {
      log.warn("target exception is null. return defaultExceptionCode.");
      return defaultExceptionCode;
    }

    if (ex instanceof ExceptionCodeProvider) {
      String code = ((ExceptionCodeProvider) ex).getCode();
      if (code != null) {
        return code;
      }
    }

    if (exceptionMappings == null || exceptionMappings.isEmpty()) {
      return defaultExceptionCode;
    }

    for (Entry<String, String> entry : exceptionMappings.entrySet()) {
      String targetException = entry.getKey();
      Class<?> exceptionClass = ex.getClass();
      while (exceptionClass != Object.class) {
        if (exceptionClass.getName().contains(targetException)) {
          return entry.getValue();
        }
        exceptionClass = exceptionClass.getSuperclass();
      }
    }

    return defaultExceptionCode;
  }

  @Override
  public String resolveExceptionCode(String defaultExceptionCode) {
    if (defaultExceptionCode == null) {
      log.warn("target exception is null. return defaultExceptionCode.");
      return null;
    }

    if (exceptionMappings == null || exceptionMappings.isEmpty()) {
      return defaultExceptionCode;
    }
    Optional<Entry<String, String>> foundEntry = exceptionMappings.entrySet().stream()
        .filter(entry -> entry.getKey().equals(defaultExceptionCode)).findFirst();

    if (foundEntry.isPresent()) {
      return foundEntry.get().getValue();
    }

    return defaultExceptionCode;
  }

}
