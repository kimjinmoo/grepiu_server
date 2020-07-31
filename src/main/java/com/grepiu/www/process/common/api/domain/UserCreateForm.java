package com.grepiu.www.process.common.api.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grepiu.www.process.common.security.domain.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserCreateForm {

  @NotEmpty
  private String id;

  @NotEmpty
  private String password;

//  @NotEmpty
//  private String passwordRepeated = "";

  @NotNull
  @Schema(hidden = true)
  private Role role = Role.USER;
}
