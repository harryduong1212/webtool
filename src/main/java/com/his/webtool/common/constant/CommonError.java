package com.his.webtool.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonError {

    // Invalid Request
    public static final String CR1016 = "CR1016";
    // Resource not found: {0}
    public static final String CR1024 = "CR1024";
    // Unable to insert into {0}
    public static final String CR1025 = "CR1025";
    // Unable to obtain {0}
    public static final String CR1026 = "CR1026";
    // Unable to update {0}
    public static final String CR1027 = "CR1027";
    // Unable to delete {0}
    public static final String CR1028 = "CR1028";
}
