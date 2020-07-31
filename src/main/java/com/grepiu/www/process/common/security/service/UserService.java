package com.grepiu.www.process.common.security.service;

import com.grepiu.www.process.common.api.domain.UserPasswordUpdateForm;
import com.grepiu.www.process.common.security.dao.GrepUserRepository;
import com.grepiu.www.process.common.security.entity.User;
import com.grepiu.www.process.common.api.domain.UserCreateForm;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final GrepUserRepository grepUserRepository;

  public Optional<User> findUserById(String id) {
    return grepUserRepository.findUserByIdAndActiveTrue(id);
  }

  // 유저 등록
  public User saveUser(UserCreateForm form) {
    User user = User.build(form.getId(), form.getPassword(), form.getRole(), true);
    return grepUserRepository.save(user);
  }

  // 유저 비밀번호 수정(비밀번호 확인)
  public User updatePassword(UserPasswordUpdateForm form) throws Exception {
    User user = grepUserRepository.findUserByIdAndActiveTrue(form.getId()).orElseThrow(Exception::new);
    user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getChangedPassword()));
    return grepUserRepository.save(user);
  }

  // 유저 비밀번호 수정(관리자용)
  public User updatePassword(String id, String password) {
    User user = grepUserRepository.findUserByIdAndActiveTrue(id).get();
    user.setPasswordHash(new BCryptPasswordEncoder().encode(password));

    return grepUserRepository.save(user);
  }
}
