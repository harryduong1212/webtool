package com.his.webtool.service.impl;

import com.his.webtool.common.mapper.ModelConverter;
import com.his.webtool.entity.UserInform;
import com.his.webtool.repository.UserInformRepository;
import com.his.webtool.service.UserRegister;
import com.his.webtool.service.template.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl extends BaseService<UserRegister.Input, UserRegister.Output>
        implements UserRegister {

    private final ModelConverter modelConverter;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserInformRepository userInformRepository;

    @Override
    protected void preExecute(Input input) {
        // do nothing
    }

    @Override
    protected Mono<Output> doExecute(Input input) {
        return userInformRepository.save(modelConverter.map(input, UserInform.class)
                        .setPassword(bCryptPasswordEncoder.encode(input.getPassword())))
                .map(userInform -> new Output(userInform.getId(), userInform.getUsername()));
    }

    @Override
    protected void postExecute(Input input) {
        // do nothing
    }
}
