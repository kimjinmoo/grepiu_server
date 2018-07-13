package com.grepiu.test.process.common;


import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.Role;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.auth.service.UserService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LoginTDD extends LocalBaseConfig {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void setUp() {
    userRepository.deleteAll();
  }

  @Test
  public void searchId() {
    Optional<User> user = userRepository.findUserById("iukim21c@gmail.com");
    if(user.isPresent()) {
      log.debug("user : {} ", user.get());
    }
  }

  @Test
  public void createUser() {
    //given
    User user = User.build("iukim21c@gmail.com","xptmxm1!@", Role.USER);
    //when
    userRepository.save(user);
    Optional<User> userCheck = userService.findUserById("iukim21c@gmail.com");
    //then
    Assert.assertTrue(userCheck.isPresent());
  }
}
