package com.his.webtool.config;

import com.his.webtool.exception.ResourceNotFoundException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public Map<Class<? extends Throwable>, HttpStatus> exceptionToStatusCode() {
        Map<Class<? extends Throwable>, HttpStatus> map = new HashMap<>();
        map.put(ResourceNotFoundException.class, HttpStatus.NOT_FOUND);
        map.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        return map;
    }

    @Bean
    public HttpStatus defaultStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}