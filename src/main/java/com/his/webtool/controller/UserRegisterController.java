package com.his.webtool.controller;

import com.his.webtool.common.mapper.ModelConverter;
import com.his.webtool.dto.request.UserRegisterRequest;
import com.his.webtool.dto.response.UserRegisterResponse;
import com.his.webtool.service.UserRegister;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRegisterController extends BaseRestController {

    private final ModelConverter modelConverter;

    private final UserRegister userRegister;

    @PostMapping("/register")
    public Mono<UserRegisterResponse> search(@RequestBody @Valid UserRegisterRequest request) {
        UserRegister.Input input = modelConverter.map(request, UserRegister.Input.class);
        return userRegister.execute(input)
                .map(output -> modelConverter.map(output, UserRegisterResponse.class));
    }

}
