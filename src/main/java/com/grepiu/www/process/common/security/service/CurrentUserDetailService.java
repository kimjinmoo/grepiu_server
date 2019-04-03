package com.grepiu.www.process.common.security.service;

import com.grepiu.www.process.common.security.domain.CurrentUser;
import com.grepiu.www.process.common.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * 스프링 Security 설정
 * 유저 정보
 *
 */
@Service
public class CurrentUserDetailService implements UserDetailsService {

  @Autowired
  private final UserService userService;

  @Autowired
  public CurrentUserDetailService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    User user = userService.findUserById(id).orElseThrow(()-> new UsernameNotFoundException(String.format("사용자를 찾을수 없습니다 id=%s",id)));
    return new CurrentUser(user);
  }
}
