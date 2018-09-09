package com.grepiu.www.process.common.api.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grepiu.www.process.common.security.domain.Role;
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
