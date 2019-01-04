package com.grepiu.www.process.common.security.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Builder
@Document(collection = "member")
public class User implements Serializable {

  @Id
  private String id;

  private String passwordHash;

  private Role role;

  @CreatedDate
  private Date createDate;

  @LastModifiedDate
  private Date modifyDate;

  public static User build(String id, String plainPassword, Role role) {
    return builder().id(id).passwordHash(new BCryptPasswordEncoder().encode(plainPassword)).role(role).build();
  }
}
