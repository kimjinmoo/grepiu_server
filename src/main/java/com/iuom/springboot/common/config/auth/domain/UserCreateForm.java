package com.iuom.springboot.common.config.auth.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateForm {

  @NotEmpty
  private String email = "";

  @NotEmpty
  private String password = "";

  @NotEmpty
  private String passwordRepeated = "";

  @NotNull
  private Role role = Role.USER;
}
