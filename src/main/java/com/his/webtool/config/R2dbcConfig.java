package com.his.webtool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.List;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.his.webtool.repository")
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(List<Converter<?, ?>> converters) {
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters.toArray());
    }

}