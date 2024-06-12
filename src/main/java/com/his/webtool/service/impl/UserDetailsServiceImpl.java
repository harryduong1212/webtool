package com.his.webtool.service.impl;

import com.his.webtool.config.UserDetailPrinciple;
import com.his.webtool.repository.UserInformRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserInformRepository userInformRepository;

    /**
     * All-args constructor
     *
     * @param userInformRepository {@link UserInformRepository}
     */
    public UserDetailsServiceImpl(UserInformRepository userInformRepository) {
        this.userInformRepository = userInformRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userInformRepository.findUserInformByUsername(username)
                .map(UserDetailPrinciple::fromUserInform);
    }
}