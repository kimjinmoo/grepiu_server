package com.grepiu.www.process.common.config.auth.controller;

import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.auth.domain.UserCreateForm;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 기본 베이스 컨트롤러이다.
 *
 */
@Controller
@Slf4j
public class BaseController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/login")
  public String login(ModelMap model) {
    return "login";
  }

  @GetMapping("/signUp")
  public String signUpForm() {
    return "signUp";
  }

  @PostMapping("/signUp")
  public @ResponseBody
  Object signUp(@Valid UserCreateForm form) throws Exception {
    if(userRepository.findUserById(form.getId()).isPresent()){
      throw new ValidationException("중복된 사용자가 존재 합니다.");
    }
    User user = User.build(form.getId(), form.getPassword(), form.getRole());
    return userRepository.save(user);
  }

  @GetMapping("/user")
  public @ResponseBody Object user(Principal principal) {
    return principal.getName();
  }


  @GetMapping("/")
  public String home(ModelMap model) {
    return "home";
  }
}
