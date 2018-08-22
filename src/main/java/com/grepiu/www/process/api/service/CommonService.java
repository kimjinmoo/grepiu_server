package com.grepiu.www.process.api.service;

import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 공통 서비스
 *
 */
@Service
public class CommonService {

  @Autowired
  private UserRepository userRepository;

  /**
   *
   * 유저 등록
   *
   * @param user User객체
   * @return
   */
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  /**
   *
   * 유저들 정보를 가져온다.
   *
   * @return List<User>
   */
  public List<User> getUsers()  {
    return userRepository.findAll();
  }
}
