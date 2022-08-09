package com.his.webtool.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Set;

@Data
@Entity
@Table
public class Authority {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private HashMap<String, Object> name;

  @Column(name = "options")
  private HashMap<String, Object> options;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<Role> roles;
}
