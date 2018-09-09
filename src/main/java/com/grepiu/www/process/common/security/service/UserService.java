package com.grepiu.www.process.common.security.service;

import com.grepiu.www.process.common.security.dao.UserRepository;
import com.grepiu.www.process.common.security.domain.User;
import com.grepiu.www.process.common.api.domain.UserCreateForm;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Optional<User> findUserById(String id) {
    return userRepository.findUserById(id);
  }

  public User saveUser(UserCreateForm form) {
    User user = User.build(form.getId(), form.getPassword(), form.getRole());
    return userRepository.save(user);
  }
}
