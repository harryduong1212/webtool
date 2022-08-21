/**
 * Wincal-X Project
 * BaseResponseHttpResponseWrapper.java
 * Copyright © ALMEX Inc. All rights reserved.
 */
package com.his.webtool.wrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import com.his.webtool.provider.ServletOutputStreamCopier;

/**
 * BaseResponseHttpResponseWrapper
 */
public class BaseResponseHttpResponseWrapper extends HttpServletResponseWrapper {

  private ServletOutputStream outputStream;
  private PrintWriter writer;
  private ServletOutputStreamCopier copier;

  public BaseResponseHttpResponseWrapper(HttpServletResponse response) throws IOException {
    super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException("getWriter() has already been called on this response.");
    }

    if (outputStream == null) {
      outputStream = getResponse().getOutputStream();
      copier = new ServletOutputStreamCopier(outputStream);
    }

    return copier;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (outputStream != null) {
      throw new IllegalStateException(
          "getOutputStream() has already been called on this response.");
    }

    if (writer == null) {
      copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
      writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()),
          true);
    }

    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    if (writer != null) {
      writer.flush();
    } else if (outputStream != null) {
      copier.flush();
    }
  }

  public byte[] getCopy() {
    if (copier != null) {
      return copier.getCopy();
    } else {
      return new byte[0];
    }
  }
}
