package com.grepiu.www.process.common.config.auth.controller;

import com.grepiu.www.process.common.config.Oauth2ClientConfig;
import com.grepiu.www.process.common.config.YAMLConfig;
import com.grepiu.www.process.common.config.auth.domain.LoginForm;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  private TokenStore tokenStore;

  @Value("${grepiu.oauth.token}")
  private String tokenUrl;

  @ApiOperation("Oauth 기반 로그인")
  @PostMapping("/oauth/login")
  public ResponseEntity<Object> oauthLogin(@RequestBody LoginForm form) {
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    resourceDetails.setAccessTokenUri(tokenUrl);
    resourceDetails.setClientId("grepiu-client");
    resourceDetails.setClientSecret("grepiu-secret");
    resourceDetails.setGrantType("password");
    resourceDetails.setUsername(form.getId());
    resourceDetails.setPassword(form.getPassword());

    OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
    oAuthRestTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
    OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();

    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @ApiOperation("Oauth 기반 로그인")
  @PostMapping("/oauth/logout")
  public ResponseEntity<Void> oauthLogout(@RequestHeader("Authorization") String authorization) {
    String tokenValue = authorization.replace("Bearer", "").trim();
    OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
    tokenStore.removeAccessToken(accessToken);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/me")
  public @ResponseBody
  Object user(Principal principal) {
    return principal;
  }
}
