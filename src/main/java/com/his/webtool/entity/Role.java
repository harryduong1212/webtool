package com.his.webtool.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Data
@Entity
@Table
public class Role {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private HashMap<String, Object> name;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<Authority> authorities;
}
