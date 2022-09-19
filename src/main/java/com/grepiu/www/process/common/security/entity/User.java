package com.grepiu.www.process.common.security.entity;

import com.grepiu.www.process.common.security.domain.Role;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "member")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String id;
  // 이름
  private String name;
  // 프로파일
  private String profile;

  private String passwordHash;

  private Role role;

  @NotNull
  private boolean active = true;

  @CreatedDate
  private Date createDate;

  @LastModifiedDate
  private Date modifyDate;

  public static User build(String id, String plainPassword, String name, String profile, Role role, boolean isActive) {
    return builder()
        .id(id)
        .name(name)
        .profile(profile)
        .passwordHash(plainPassword)
        .role(role).active(isActive).build();
  }
}
