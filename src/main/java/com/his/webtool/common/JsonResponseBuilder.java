package com.his.webtool.common;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonResponseBuilder {

  public static Map<String, Object> create(int status, String reason, String route, Object body) {
    return create(status, reason, route, body, null);
  }

  public static Map<String, Object> create(
      int status, String reason, String route, Object body, Object errors) {
    Map<String, Object> wrappedMap = new LinkedHashMap<>();
    wrappedMap.put("status", status);
    wrappedMap.put("reason", reason);
    wrappedMap.put("date", OffsetDateTime.now());
    wrappedMap.put("route", route);
    wrappedMap.put("body", body);
    wrappedMap.put("errors", errors);
    return wrappedMap;
  }
}
