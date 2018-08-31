package com.grepiu.www.process.common.config.auth.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserCreateForm {

  @Id
  @NotEmpty
  private String id = "";

  @NotEmpty
  private String password = "";

  @NotEmpty
  private String passwordRepeated = "";

  @NotNull
  private Role role = Role.USER;
}
