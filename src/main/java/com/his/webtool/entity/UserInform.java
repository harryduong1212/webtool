package com.his.webtool.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@Table("user_inform")
public class UserInform {

    /**
     * All-args constructor
     *
     * @param username    username
     * @param password    password
     * @param firstName   first name
     * @param lastName    last name
     * @param dayOfBirth  day of birth
     * @param address     address
     * @param phoneNumber phone number
     * @param email       email
     * @param roleCode    role
     */
    public UserInform(String username, String password, String firstName, String lastName, LocalDate dayOfBirth,
            Map<String, Object> address, String phoneNumber, String email, String roleCode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roleCode = roleCode;
    }

    @Id
    private Long id;
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
