package com.iuom.springboot.common.config.auth.domain;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

  private User user;

  public CurrentUser(User user) {
    super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
    this.user = user;
  }

  public String getEmail(){return user.getEmail();}
  public Role getRole(){return user.getRole();}
}
