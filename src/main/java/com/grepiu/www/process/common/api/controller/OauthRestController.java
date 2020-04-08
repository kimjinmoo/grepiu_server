package com.grepiu.www.process.common.api.controller;

import com.grepiu.www.process.common.api.base.GrepIUResponse;
import com.grepiu.www.process.common.api.domain.LoginForm;
import com.grepiu.www.process.common.api.domain.UserPasswordUpdateForm;
import com.grepiu.www.process.common.api.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.api.service.BaseServiceImpl;
import com.grepiu.www.process.common.security.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Oauth 관련 컨트롤러
 *
 */
@RequestMapping("/oauth")
@RestController
@Slf4j
public class OauthRestController {

  private final BaseServiceImpl baseService;

  private final UserService userService;

  public OauthRestController(BaseServiceImpl baseService,
      UserService userService) {
    this.baseService = baseService;
    this.userService = userService;
  }

  @ApiOperation("Oauth 기반 로그인")
  @PostMapping("/login")
  public ResponseEntity<Object> oauthLogin(@RequestBody LoginForm form) throws LoginErrPasswordException {
    return new ResponseEntity<>(baseService.login(form), HttpStatus.OK);
  }

  @ApiOperation("Oauth 로그 아웃")
  @PostMapping("/logout")
  public ResponseEntity<Object> oauthLogout(@RequestHeader("Authorization") String authorization) {
    return new ResponseEntity<>(baseService.logout(authorization.replace("Bearer", "").trim()), HttpStatus.OK);
  }

  @ApiOperation("비밀번호 초기화")
  @PostMapping("/reset")
  public ResponseEntity<Object> resetPassword(@RequestParam String id) throws Exception {
    return new ResponseEntity<>(baseService.resetPassword(id), HttpStatus.OK);
  }

  @ApiOperation("Oauth 토큰 유효성 체크")
  @GetMapping("/check")
  public ResponseEntity<Object> oauthTokenCheck(@RequestParam("accessToken") String accessToken) {
    return new ResponseEntity<Object>(baseService.isValidToken(accessToken), HttpStatus.OK);
  }

  @ApiOperation("refresh 토큰으로 엑세스 토큰을 갱신한다.")
  @PostMapping("/refresh")
  public ResponseEntity<Object> oauthGetTokenByRefresh(@RequestParam("refreshToken") String refreshToken) {
    return new ResponseEntity<>(baseService.refreshToken(refreshToken), HttpStatus.OK);
  }

  @ApiOperation("유저 정보를 가져온다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @GetMapping("/users/me")
  public Object user(Principal principal) {
    return principal;
  }

  @ApiOperation("유저 비밀번호를 변경 한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @PutMapping("/users/password")
  public Object updatePassword(Principal principal, @RequestBody UserPasswordUpdateForm form)
      throws Exception {
    //set ID
    form.setId(principal.getName());
    return new GrepIUResponse().ok(userService.updatePassword(form));
  }

  @ApiOperation("유저 회원 탈퇴")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @PostMapping("/users/leave")
  public Object leave(Authentication authentication) throws Exception {
    return new GrepIUResponse().ok(baseService.leaveUser(authentication));
  }
}
