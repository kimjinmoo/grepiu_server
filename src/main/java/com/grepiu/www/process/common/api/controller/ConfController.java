package com.grepiu.www.process.common.api.controller;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.api.domain.UserCreateForm;
import com.grepiu.www.process.common.api.service.BaseService;
import com.grepiu.www.process.common.security.domain.Role;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Conf 도메인 컨트롤러
 *
 */
@Controller
@Slf4j
public class ConfController {

  private final BaseService baseService;

  public ConfController(BaseService baseService) {
    this.baseService = baseService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/signUp")
  public String signUpForm() {
    return "signUp";
  }

  @ApiOperation("회원가입")
  @PostMapping("/signUp")
  public @ResponseBody ResponseEntity<Object> signUp(@Valid UserCreateForm form) throws Exception {
    // 일반유저
    form.setRole(Role.USER);
    return new ResponseEntity<>(
        baseService.signUp(form),
        HttpStatus.OK);
  }

  @ApiOperation("회원가입 - App 회원 가입")
  @PostMapping("/app/signUp")
  public @ResponseBody ResponseEntity<Object> signUpApp(@Valid UserCreateForm form) throws Exception {
    //set App
    form.setRole(Role.GS_APP);
    // Save
    return new ResponseEntity<>(
            baseService.signUp(form),
            HttpStatus.OK);
  }

  @ApiOperation("회원가입 - Party 회원 가입")
  @PostMapping("/party/signUp")
  public @ResponseBody ResponseEntity<Object> signUpPartyApp(@Valid UserCreateForm form) throws Exception {
    //set App
    form.setRole(Role.GS_PARTY_APP);
    // Save
    return new ResponseEntity<>(
        baseService.signUp(form),
        HttpStatus.OK);
  }

  @ApiOperation("ID 중복 확인")
  @GetMapping("/app/signUp/{id}")
  public @ResponseBody ResponseEntity<Object> checkDuplicatedId(@PathVariable("id") String id) {
    // set result
    HashMap<String, Boolean> resultMap = Maps.newHashMap();
    resultMap.put("duplicated", baseService.getUserById(id).isPresent());
    // result
    return ResponseEntity.ok(resultMap);
  }

  @GetMapping("/")
  public String home(ModelMap model) {
    return "home";
  }

  @GetMapping("/me")
  public @ResponseBody Object me() {
    HashMap<String, String> obj = Maps.newHashMap();
    obj.put("test","test");
    return obj;
  }

}
