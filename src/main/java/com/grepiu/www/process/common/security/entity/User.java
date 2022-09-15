package com.grepiu.www.process.common.security.entity;

import com.grepiu.www.process.common.security.domain.Role;
import java.io.Serializable;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotNull;

@Data
@Builder
@Document(collection = "member")
public class User implements Serializable {

  @Id
  private String id;

  private String passwordHash;

  private Role role;

  @NotNull
  private boolean active = true;

  @CreatedDate
  private Date createDate;

  @LastModifiedDate
  private Date modifyDate;

  public static User build(String id, String plainPassword, Role role, boolean isActive) {
    return builder()
        .id(id)
        .passwordHash(plainPassword)
        .role(role).active(isActive).build();
  }
}
