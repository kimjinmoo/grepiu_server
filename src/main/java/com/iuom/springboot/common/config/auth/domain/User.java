package com.iuom.springboot.common.config.auth.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Builder
@Document(collection = "member")
public class User {

  @Id
  private String id;

  private String email;

  private String passwordHash;

  private Role role;

  public static User build(String email, String plainPassword, Role role) {
    return builder().email(email).passwordHash(new BCryptPasswordEncoder().encode(plainPassword)).role(role).build();
  }
}
