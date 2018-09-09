package com.grepiu.www.process.common.config.auth.controller;

import com.grepiu.www.process.common.config.Oauth2ClientConfig;
import com.grepiu.www.process.common.config.YAMLConfig;
import com.grepiu.www.process.common.config.auth.domain.CurrentUser;
import com.grepiu.www.process.common.config.auth.domain.LoginForm;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.auth.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.config.auth.service.BaseService;
import com.grepiu.www.process.common.config.auth.service.CurrentUserDetailService;
import com.grepiu.www.process.common.config.auth.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 *
 */
@RestController
@Slf4j
public class BaseRestController {

  @Autowired
  private BaseService baseService;

  @ApiOperation("Oauth 기반 로그인")
  @PostMapping("/oauth/login")
  public ResponseEntity<Object> oauthLogin(@RequestBody LoginForm form) throws LoginErrPasswordException {
    return new ResponseEntity<>(baseService.login(form), HttpStatus.OK);
  }

  @ApiOperation("Oauth 로그 아웃")
  @PostMapping("/oauth/logout")
  public ResponseEntity<Object> oauthLogout(@RequestHeader("Authorization") String authorization) {
    return new ResponseEntity<>(baseService.logout(authorization.replace("Bearer", "").trim()), HttpStatus.OK);
  }

  @ApiOperation("Oauth 토큰 유효성 체크")
  @PostMapping("/oauth/check")
  public ResponseEntity<Object> oauthTokenCheck(@RequestHeader("Authorization") String authorization) {
    return new ResponseEntity<>(baseService.isValidToken(authorization.replace("Bearer", "").trim()), HttpStatus.OK);
  }

  @ApiOperation("유저 정보를 가져온다.")
  @GetMapping("/users/me")
  public @ResponseBody
  Object user(Principal principal) {
      return principal;
  }
}
