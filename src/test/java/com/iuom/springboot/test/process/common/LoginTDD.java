package com.iuom.springboot.test.process.common;

import com.iuom.springboot.common.config.auth.dao.UserRepository;
import com.iuom.springboot.common.config.auth.domain.Role;
import com.iuom.springboot.common.config.auth.domain.User;
import com.iuom.springboot.common.config.auth.service.UserService;
import com.iuom.springboot.test.process.config.IntegrationTestBase;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LoginTDD extends IntegrationTestBase {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  /**
   *
   * 초기화
   *
   */
  @Before
  public void setup() {
    userRepository.deleteAll();
  }

  @Test
  public void createUser() {
    //given
    User user = User.build("iukim21c@gmail.com","test", Role.USER);
    //when
    userRepository.save(user);
    Optional<User> userCheck = userService.findUserByEmail("iukim21c@gmail.com");
    //then
    Assert.assertTrue(userCheck.isPresent());
  }
}
