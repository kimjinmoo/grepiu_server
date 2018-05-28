package com.grepiu.www.process.common.config.auth.service;

import com.grepiu.www.process.common.config.auth.domain.CurrentUser;
import com.grepiu.www.process.common.config.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailService implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public CurrentUserDetailService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format("사용자를 찾을수 없습니다 email=%s",email)));
    return new CurrentUser(user);
  }
}
