package com.grepiu.www.process.common.api.service;

import com.grepiu.www.process.common.api.domain.LoginForm;
import com.grepiu.www.process.common.api.domain.UserPasswordUpdateForm;
import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.common.api.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.security.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;

/**
 *
 * 기본 서비스
 *
 */
public interface BaseService {

  // DB 시퀀스 생성
  public long getNextSequence(String seqName);

  // 로그인 하기
  public Object login(LoginForm form) throws LoginErrPasswordException;

  // 로그 아웃 하기
  public Object logout(String accessToken);

  // 토큰 유효성 확인
  public Object isValidToken(String accessToken);

  // 가입하기
  public User signUp(User user);

  // 비밀번호 Reset
  public Object resetPassword(String email) throws Exception;

  // 비밀번호 변경
  public User changePassword(UserPasswordUpdateForm form) throws Exception;

  // 유저 정보 가져오기
  public List<User> getUsers();

  // 유저 정보 가져오기
  public Optional<User> getUserById(String id);

  // 탈퇴하기
  public Object leaveUser(Authentication authentication) throws Exception;

  // 파일 업로드 정보 가져오기
  public List<Files> getUploadFileList();
}
