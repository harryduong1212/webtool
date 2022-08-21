/**
 * Wincal-X Project
 * BaseRequestHttpRequestWrapper.java
 * Copyright © ALMEX Inc. All rights reserved.
 */
package com.his.webtool.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import com.his.webtool.common.constant.CommonError;
import com.his.webtool.exception.InvalidRequestException;
import lombok.extern.log4j.Log4j2;

/**
 * BaseRequestHttpRequestWrapper
 */
@Log4j2
public class BaseRequestHttpRequestWrapper extends HttpServletRequestWrapper {

  private final String body;

  public BaseRequestHttpRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;
    try (InputStream inputStream = request.getInputStream()) {
      if (Objects.nonNull(inputStream)) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
      }
    } catch (IOException ex) {
      log.error(ex);
      throw new InvalidRequestException(CommonError.CR1016);
    } finally {
      // TODO: need recheck if removable
      if (bufferedReader != null) {
        bufferedReader.close();
      }
    }

    body = stringBuilder.toString();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
    return new ServletInputStream() {
      public int read() {
        return byteArrayInputStream.read();
      }

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {}
    };
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }

  public String getBody() {
    return this.body;
  }
}
