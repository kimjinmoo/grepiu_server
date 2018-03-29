package com.iuom.springboot.common.config.auth.service;

import com.iuom.springboot.common.config.auth.dao.UserRepository;
import com.iuom.springboot.common.config.auth.domain.User;
import com.iuom.springboot.common.config.auth.domain.UserCreateForm;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Optional<User> findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  public User saveUser(UserCreateForm form) {
    User user = User.build(form.getEmail(), form.getPassword(), form.getRole());
    return userRepository.save(user);
  }
}
