package com.grepiu.www.process.common.api.domain;

import com.grepiu.www.process.common.security.domain.Role;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserPasswordUpdateForm {

  @Id
  @NotEmpty
  private String id;

  @NotEmpty
  private String changedPassword;
}
