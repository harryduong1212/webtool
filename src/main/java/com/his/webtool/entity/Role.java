package com.his.webtool.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@Data
@Table("role")
public class Role {

    @Id
    private String code;
    private Map<String, Object> name;

}
