package com.grepiu.www.process.common.api.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.grepiu.www.process.common.security.domain.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserCreateForm {

  @Id
  @NotEmpty
  private String id = "";

  @NotEmpty
  private String password = "";

  private String name;

  private String profile;

//  @NotEmpty
//  private String passwordRepeated = "";

  @NotNull
  @ApiModelProperty(hidden = true)
  private Role role = Role.USER;
}
