package com.his.webtool.converter;

import io.r2dbc.postgresql.codec.Json;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@WritingConverter
@RequiredArgsConstructor
public class MapToJsonConverter implements Converter<Map<String, Object>, Json> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Json convert(@NonNull Map<String, Object> toConvert) {
        try {
            return Json.of(objectMapper.writeValueAsString(toConvert));
        } catch (IOException e) {
            log.error("Map serialization failed: {}", e.getMessage());
            return Json.of(StringUtils.EMPTY);
        }
    }
}