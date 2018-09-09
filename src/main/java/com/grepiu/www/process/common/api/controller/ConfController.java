package com.grepiu.www.process.common.api.controller;

import com.grepiu.www.process.common.api.domain.Files;
import com.grepiu.www.process.common.helper.FileHelper;
import com.grepiu.www.process.common.security.dao.UserRepository;
import com.grepiu.www.process.common.security.domain.User;
import com.grepiu.www.process.common.api.domain.UserCreateForm;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * Conf 도메인 컨트롤러
 *
 */
@Controller
@Slf4j
public class ConfController {

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

  @ApiOperation("회원가입")
  @PostMapping("/signUp")
  public @ResponseBody ResponseEntity<Object> signUp(@Valid UserCreateForm form) throws Exception {
    if(userRepository.findUserById(form.getId()).isPresent()){
      throw new ValidationException("중복된 사용자가 존재 합니다.");
    }
    User user = User.build(form.getId(), form.getPassword(), form.getRole());
    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
  }

  @GetMapping("/")
  public String home(ModelMap model) {
    return "home";
  }

}
