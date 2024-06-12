package com.his.webtool.service;

import com.his.webtool.service.template.IService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

public interface UserRegister extends IService<UserRegister.Input, UserRegister.Output> {

    @Data
    class Input {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private LocalDate dayOfBirth;
        private Map<String, Object> address;
        private String phoneNumber;
        private String email;
        private String roleCode;
    }

    @Data
    @AllArgsConstructor
    class Output {
        private Long id;
        private String username;
    }

}
