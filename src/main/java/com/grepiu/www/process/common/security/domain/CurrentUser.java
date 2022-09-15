package com.grepiu.www.process.common.security.domain;

import com.grepiu.www.process.common.security.entity.User;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 *
 * 유저 정보 재정의
 *
 */
@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

  private User user;

  public CurrentUser(User user) {
    super(user.getId(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().name()));
    this.user = user;
  }

  public String getId(){return user.getId();}
  public Role getRole(){return user.getRole();}
}
