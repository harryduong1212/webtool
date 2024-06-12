package com.his.webtool.dto.request;

import com.his.webtool.common.constant.RegexConst;
import com.his.webtool.dto.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterRequest extends BaseRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private LocalDate dayOfBirth;

    private Map<String, Object> address;

    @NotBlank
    @Pattern(regexp = RegexConst.PHONE_NUMBER)
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = RegexConst.EMAIL_SIMPLE)
    private String email;

    private String roleCode;

}
