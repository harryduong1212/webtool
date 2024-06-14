package com.his.webtool.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Object trace;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiError> details = new ArrayList<>();

    public ApiError(String code, String message) {
        this(code, message, null);
    }

    public ApiError(String code, String message, String target) {
        this(code, message, target, null);
    }

    public void addDetail(ApiError detail) {
        details.add(detail);
    }

}
