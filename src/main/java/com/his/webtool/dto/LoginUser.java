package com.his.webtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String roleCode;

    public static LoginUser of(Long id, String username, String roleCode) {
        return new LoginUser(id, username, roleCode);
    }
}
