package com.grepiu.www.process.common.config.auth.domain;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

  public static User build(String id, String plainPassword, Role role) {
    return builder().id(id).passwordHash(new BCryptPasswordEncoder().encode(plainPassword)).role(role).build();
  }
}
