package com.his.webtool.config;

import com.his.webtool.entity.UserInform;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailPrinciple implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    /**
     * Construct {@link UserDetailPrinciple} by given {@link UserInform}
     *
     * @param userInform {@link UserInform}
     * @return {@link UserDetailPrinciple}
     */
    public static UserDetailPrinciple fromUserInform(final UserInform userInform) {
        return new UserDetailPrinciple(userInform.getUsername(), userInform.getPassword(), new ArrayList<>());
    }

}
