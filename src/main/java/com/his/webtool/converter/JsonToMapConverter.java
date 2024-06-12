package com.his.webtool.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import io.r2dbc.postgresql.codec.Json;

import java.util.Map;

@Slf4j
@Component
@ReadingConverter
@RequiredArgsConstructor
public class JsonToMapConverter implements Converter<Json, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public Map<String, Object> convert(@NonNull Json jsonString) {
        try {
            return objectMapper.readValue(jsonString.asString(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("Json deserialization failed: {}", e.getMessage());
            return Map.of();
        }
    }

}
